package no.bouvet.kpro.renderer.audio;

import java.util.ArrayList;
import java.util.List;
import no.bouvet.kpro.renderer.AbstractRenderer;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.renderer.Renderer;
import no.vdvil.renderer.audio.AudioMixer;
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
public class AudioRenderer extends AbstractRenderer implements Runnable, Renderer {

    final AudioTarget audioTarget;
    protected boolean _timeSource = false;

    protected Thread _thread;

    protected int _time;
    protected boolean _finished;

    protected List<AudioInstruction> _active = new ArrayList<>();
    final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Construct a new AudioRenderer instance that will mix audio and send it to
     * the given AudioTarget.
     *
     * @param target the AudioTarget to send audio to
     */
    public AudioRenderer(AudioTarget target) {
        this.audioTarget = target;
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
     */
    @Override
    public void start(int time) {
        stop();

        log.debug("Starting at " + ( (float)time / Instruction.RESOLUTION ) + "s with frame size " + ( (float) AudioMixer.MIX_FRAME / Instruction.RESOLUTION ) + "s" );

        audioTarget.flush();

        _time = time;
        _finished = false;

        _thread = new Thread(this);
        _thread.start();
    }

    /**
     * Stop this AudioRenderer.
     */
    @Override
    public void stop() {
        if (_thread != null) {
            Thread thread = _thread;
            _thread = null;

            audioTarget.flush();

            while (thread.isAlive()) {
                try {
                    thread.join();
                } catch (Exception e) {
                }
            }

            audioTarget.flush();
            _active.clear();

            log.debug("Stopped" );
        }
    }

    /**
     * Handle a rendering AbstractInstruction. This method is called by the master
     * Renderer as time passes within the instruction list. The time parameter
     * specifies the current rendering time. The instruction parameter specifies
     * a rendering instruction event that has occurred.
     *
     * The start time of the AbstractInstruction will always be less than or equal to
     * the time parameter. If it were greater, it would not have occurred yet.
     *
     * The start time of the AbstractInstruction may be significantly less than the time
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
     * AudioInstructions. All other AbstractInstruction types are ignored.
     *
     * @param beat
     *            the current rendering time in samples
     * @param instruction
     *            the instruction that has occurred, or null
     */
    public void notify(Instruction instruction, long beat) {
        if(instruction != null) {
            if (instruction instanceof AudioInstruction) {
                log.info("BPM: {} start:{} actual:{}", instruction, instruction.start(), beat);
                _active.add((AudioInstruction) instruction);
            }
        }
    }

    public boolean isRendering() {
        return !_finished;
    }

    public void stop(Instruction instruction) {
        //AudioRenderer handles stopping playback itself
        _finished = true;
    }

    /**
     * This is the implementation of Runnable.run, and is the main thread
     * procedure.
     *
     * The AudioRenderer's thread mixes audio and sends it to the AudioTarget.
     * It may also provide time source information to the master OldRenderer.
     */
    public void run() {
        try {
            while (!_finished || !_active.isEmpty()) {
                if (_timeSource) {
                    _renderer.notifyTime(_time + AudioMixer.MIX_FRAME);
                }
                _active = pruneByTime(_active);

                _time = AudioMixer.mixItUp(_active, _time, audioTarget);
            }
        }finally {
            log.debug("End of composition, draining target...");
            audioTarget.drain();
            if (_timeSource) {
                _renderer.notifyFinished();
            }
        }
    }





    List<AudioInstruction> pruneByTime(List<AudioInstruction> active) {
        List<AudioInstruction> prunedList = new ArrayList<>();
        for (AudioInstruction instruction : active) {
            if(instruction.end() > _time && instruction.getSourceDuration() > 0)
                prunedList.add(instruction);
        }
        return prunedList;
    }
}