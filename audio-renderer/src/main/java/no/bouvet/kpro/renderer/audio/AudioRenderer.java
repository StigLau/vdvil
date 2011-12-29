package no.bouvet.kpro.renderer.audio;

import java.util.SortedSet;
import java.util.TreeSet;
import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;
import no.lau.vdvil.renderer.SuperRenderer;
import no.lau.vdvil.renderer.TimeSource;
import no.lau.vdvil.timing.Time;
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
public class AudioRenderer extends SuperRenderer implements TimeSource, Runnable {
	
	protected boolean _timeSource = false;

	protected Thread _thread;

	protected int _time = 0;
	protected boolean _finished = false;

    Renderer _renderer;
    AudioMixer audioMixer;
    Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Construct a new AudioRenderer instance that will mix audio and send it to
	 * the given AudioTarget.
	 *
	 * @param target the AudioTarget to send audio to
	 */
	public AudioRenderer(AudioTarget target) {
        audioMixer = new AudioMixer(target);
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
     * Start this TimeSource. The AudioRenderer will run in another thread.
     */
	public void start() {
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

            audioMixer.target.flush();

			while (thread.isAlive()) {
				try {
					thread.join();
				} catch (Exception e) {
				}
			}

            audioMixer.target.flush();
            instructionSet.clear();

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
	 * The AudioRenderer builds and maintains a list of currently active
	 * AudioInstructions. All other Instruction types are ignored.
	 * 
	 * @param time
	 *            the current rendering time in samples
	 */
    public void ping(Time time) {
        //Since AudioRenderer is a timesource, this is not needed
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
        while (!_finished || !(instructionSet.isEmpty())) {
            if (_timeSource) {
                _renderer.notifyTime(new Time(_time + AudioMixer.MIX_FRAME));
            }
            instructionSet = pruneByTime(instructionSet);

            _time = AudioMixer.mixItUp(instructionSet, _time, audioMixer);
        }
        log.debug("End of composition, draining target...");
        audioMixer.target.drain();

        if (_timeSource) {
            _renderer.notifyFinished();
        }
	}

    SortedSet<Instruction> pruneByTime(SortedSet<Instruction> active) {
        SortedSet<Instruction> prunedList = new TreeSet<Instruction>();
        for (Instruction instruction : active) {
            if(instruction._end > _time && (instruction._end-instruction._start > 0))
                prunedList.add(instruction);
        }
        return prunedList;
    }

    @Override
    protected boolean passesFilter(Instruction instruction) {
        return instruction instanceof AudioInstruction;
    }

    public void setRenderer(Renderer renderer) {
        _renderer = renderer;
    }
}
