package no.bouvet.kpro.renderer.audio;

import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The AudioRenderer class is a specialization of the AbstractRenderer class. It
 * implements an audio mixer that can handle AudioInstructions by mixing various
 * audio sources together and applying a set of effects necessary for smooth
 * audio transitions. These include volume adjustment and rate adjustment, with
 * linear interpolation supported for both.
 *
 * The AudioRenderer will always agree to become the time source if asked.
 *
 * @author Michael Stokes
 * @author Stig Lau
 */
public class AudioRenderer extends AbstractRenderer implements Runnable {
    /**
     * The size of a mixer frame, in samples. 1/10th of a second.
     */
    public final static int MIX_FRAME = 4410;

    Mixer mixer;
    protected boolean _timeSource = false;

    protected Thread _thread;

    protected int _time;
    protected boolean _finished;

    protected List<AudioInstruction> _active = new ArrayList<AudioInstruction>();
    Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Construct a new AudioRenderer instance that will mix audio and send it to
     * the given AudioTarget.
     *
     * @param target the AudioTarget to send audio to
     */
    public AudioRenderer(AudioTarget target) {
        mixer = new Mixer(target);
    }

    /**
     * Request that this AudioRenderer become the time source. It will always
     * agree.
     *
     * @return true
     */
    @Override
    public boolean requestTimeSource() {
        _timeSource = true;
        return true;
    }

    /**
     * Start this AudioRenderer, at the given point in time. The AudioRenderer
     * will run in another thread.
     *
     * @param time
     *            The time in samples when rendering begins
     * @return true
     */
    @Override
    public boolean start(int time) {
        stop();

        log.debug("Starting at " + ( (float)time / Renderer.RATE ) + "s with frame size " + ( (float)MIX_FRAME / Renderer.RATE ) + "s" );

        mixer.target.flush();

        _time = time;
        _finished = false;

        _thread = new Thread(this);
        _thread.start();

        return true;
    }

    /**
     * Stop this AudioRenderer.
     */
    @Override
    public void stop() {
        if (_thread != null) {
            Thread thread = _thread;
            _thread = null;

            mixer.target.flush();

            while (thread.isAlive()) {
                try {
                    thread.join();
                } catch (Exception e) {
                }
            }

            mixer.target.flush();
            _active.clear();

            log.debug("Stopped" );
        }
    }

    /**
     * Handle a rendering Instruction. This method is called by the master
     * Renderer as time passes within the instruction list. The time parameter
     * specifies the current rendering time. The instruction parameter specifies
     * a rendering instruction event that has occurred.
     *
     * The start time of the Instruction will always be less than or equal to
     * the time parameter. If it were greater, it would not have occurred yet.
     *
     * The start time of the Instruction may be significantly less than the time
     * parameter when rendering begins, as instructions that have already
     * started may need to be handled to bring the state up to date.
     *
     * A given instruction will only be fired once between a call to start() and
     * end(), i.e. within a rendering session.
     *
     * The instruction parameter may be null, which indicates no more
     * instructions.
     *
     * The AudioRenderer builds and maintains a list of currently active
     * AudioInstructions. All other Instruction types are ignored.
     *
     * @param time
     *            the current rendering time in samples
     * @param instruction
     *            the instruction that has occurred, or null
     */
    @Override
    public void handleInstruction(int time, Instruction instruction) {
        System.out.println("Got instruction {} to be played at {}" + time);
        if (instruction instanceof AudioInstruction) {
            _active.add((AudioInstruction) instruction);
        }
    }

    @Override
    public boolean isRendering() {
        return !_finished;
    }

    @Override
    public void stop(Instruction instruction) {
        //AudioRenderer handles stopping playback itself
        _finished = true;
    }

    /**
     * This is the implementation of Runnable.run, and is the main thread
     * procedure.
     *
     * The AudioRenderer's thread mixes audio and sends it to the AudioTarget.
     * It may also provide time source information to the master Renderer.
     */
    public void run() {
        while (!_finished || !(_active.isEmpty())) {
            if (_timeSource) {
                _renderer.notifyTime(_time + MIX_FRAME);
            }
            _active = pruneByTime(_active);

            _time = mixItUp(_active, _time, mixer);
        }
        log.debug("End of composition, draining target..." );
        mixer.target.drain();

        if (_timeSource) {
            _renderer.notifyFinished();
        }
    }

    private static int mixItUp(List<AudioInstruction> _active, int time, Mixer mixer) {
        for (int fill = 0; fill < mixer.mix.length;) {
            mixer.mix[fill++] = 0;
        }

        for (AudioInstruction instruction : _active) {
            if (instruction._start > time)
                mixer.available = instruction._start - time;
            else
                mixer.available = singlePass(instruction, time, mixer);
        }
        System.out.println("mixer.available = " + mixer.available);
        if (mixer.available > 0) {
            for (int convert = 0; convert < mixer.output.length;) {
                int v = mixer.mix[convert >>> 1];
                if (v > 32766)
                    v = 32766;
                else if (v < -32766)
                    v = -32766;
                mixer.output[convert++] = (byte) (v & 0xFF);
                mixer.output[convert++] = (byte) (v >>> 8);
            }

            int wrote = mixer.target.write(mixer.output, 0, mixer.available);
            time += wrote;
        }
        return time;
    }

    static int singlePass(final AudioInstruction instruction, int _time, Mixer mixer) {
        int duration = instruction._end - _time;

        if (instruction.getCacheExternal() != _time) {
            long external = instruction._start;
            long internal = 0;
            long frame = 0;

            while (external < _time) {
                long rate = instruction
                        .getInterpolatedRate((int) internal);
                frame = MIX_FRAME * rate / Renderer.RATE;
                internal += frame;
                external += MIX_FRAME;
            }

            if (external > _time) {
                internal -= frame;
                external -= MIX_FRAME;

                int error = _time - (int) external;
                if (error < duration)
                    duration = error;

                internal += error * frame / MIX_FRAME;
            } else {
                instruction.setCacheExternal(_time);
            }

            instruction.setCacheInternal((int) internal);
        }
        int available;
        if (duration > MIX_FRAME) {
            duration = MIX_FRAME;
            available = MIX_FRAME;
        }
        else
            available = duration;

        int internal = instruction.getCacheInternal();
        int rate = instruction.getInterpolatedRate(internal);
        int volume = instruction.getInterpolatedVolume(internal);
        int sduration = duration * rate / Renderer.RATE;

        instruction.advanceCache(duration, sduration);

        ShortBuffer source = instruction.getSource().getBuffer(
                instruction.getCue() + internal, sduration + 22050);

        if (source != null) {
            mix(source, duration, rate, volume, mixer.mix);
        }
        return available;
    }

    /**
     * Mix a range of audio samples into the mix buffer.
     *
     * @param source
     *            the source audio samples, in a pre-positioned ShortBuffer
     * @param duration
     *            the number of samples to mix, in output time
     * @param rate
     *            the rate to mix the source samples, relative to Renderer.RATE
     * @param volume
     *            the volume to mix the source samples, relative to 127
     */
    static void mix(ShortBuffer source, int duration, int rate, int volume, int[] mix) {
        int base = source.position();

        for (int time = 0, output = 0; time < duration; time++) {
            int input = base + ((time * rate / 44100) << 1);

            mix[output++] += (source.get(input) * volume) >> 7;
            mix[output++] += (source.get(input + 1) * volume) >> 7;
        }
    }

    List<AudioInstruction> pruneByTime(List<AudioInstruction> active) {
        List<AudioInstruction> prunedList = new ArrayList<AudioInstruction>();
        for (AudioInstruction instruction : active) {
            if(instruction._end > _time && instruction.getSourceDuration() > 0)
                prunedList.add(instruction);
        }
        return prunedList;
    }
}

class Mixer {
    byte[] output = new byte[AudioRenderer.MIX_FRAME * 4];
    int[] mix = new int[AudioRenderer.MIX_FRAME * 2];
    int available = -1;
    final AudioTarget target;

    Mixer(AudioTarget target) {
        this.target = target;
    }


}