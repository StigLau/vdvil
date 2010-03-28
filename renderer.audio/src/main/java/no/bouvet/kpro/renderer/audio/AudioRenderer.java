package no.bouvet.kpro.renderer.audio;

import java.nio.ShortBuffer;
import java.util.ArrayList;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;
import org.apache.log4j.Logger;

/**
 * The AudioRenderer class is a specialization of the AbstractRenderer class. It
 * implements an audio mixer that can handle AudioInstructions by mixing various
 * audio sources together and applying a set of effects necessary for smooth
 * audio transitions. These include volume adjustment and rate adjustment, with
 * linear interploation supported for both.
 * 
 * The AudioRenderer will always agree to become the time source if asked.
 * 
 * @author Michael Stokes
 */
public class AudioRenderer extends AbstractRenderer implements Runnable {
	/**
	 * The size of a mixer frame, in samples. 1/10th of a second.
	 */
	public final static int MIX_FRAME = 4410;

	protected AudioTarget _target;
	protected boolean _timeSource = false;

	protected Thread _thread;
	protected int[] _mix = new int[MIX_FRAME * 2];

	protected int _time;
	protected boolean _finished;

	protected ArrayList<AudioInstruction> _active = new ArrayList<AudioInstruction>();
    static Logger log = Logger.getLogger(AudioRenderer.class);

	/**
	 * Construct a new AudioRenderer instance that will mix audio and send it to
	 * the given AudioTarget.
	 * 
	 * @param target
	 *            the AudioTarget to send audio to
	 * @author Michael Stokes
	 */
	public AudioRenderer(AudioTarget target) {
		_target = target;
	}

	/**
	 * Request that this AudioRenderer become the time source. It will always
	 * agree.
	 * 
	 * @return true
	 * @author Michael Stokes
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
	 * @author Michael Stokes
	 */
	@Override
	public boolean start(int time) {
		stop();

		log.debug("Starting at " + ( (float)time / Renderer.RATE ) + "s with frame size " + ( (float)MIX_FRAME / Renderer.RATE ) + "s" );

		_target.flush();

		_time = time;
		_finished = false;

		_thread = new Thread(this);
		_thread.start();

		return true;
	}

	/**
	 * Stop this AudioRenderer.
	 * 
	 * @author Michael Stokes
	 */
	@Override
	public void stop() {
		if (_thread != null) {
			Thread thread = _thread;
			_thread = null;

			_target.flush();

			while (thread.isAlive()) {
				try {
					thread.join();
				} catch (Exception e) {
				}
			}

			_target.flush();
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
	 * @author Michael Stokes
	 */
	@Override
	public void handleInstruction(int time, Instruction instruction) {
		if (instruction == null) {
			_finished = true;
		} else if (instruction instanceof AudioInstruction) {
			synchronized (_active) {
				_active.add((AudioInstruction) instruction);
			}
		}
	}

	/**
	 * This is the implementation of Runnable.run, and is the main thread
	 * procedure.
	 * 
	 * The AudioRenderer's thread mixes audio and sends it to the AudioTarget.
	 * It may also provide time source information to the master Renderer.
	 * 
	 * @author Michael Stokes
	 */
	public void run() {
		byte[] output = new byte[MIX_FRAME * 4];

		while (_thread != null) {
			if (_timeSource) {
				_renderer.notifyTime(_time + MIX_FRAME);
			}

			for (int fill = 0; fill < _mix.length;) {
				_mix[fill++] = 0;
			}

			int available = MIX_FRAME;

			synchronized (_active) {
				for (int active = 0; active < _active.size(); active++) {
					AudioInstruction instruction = _active.get(active);

					if (instruction.getEnd() <= _time) {
						_active.remove(active--);
						continue;
					} else if (instruction.getStart() > _time) {
						available = instruction.getStart() - _time;
						continue;
					} else if (instruction.getSourceDuration() <= 0) {
						_active.remove(active--);
						continue;
					}

					int duration = instruction.getEnd() - _time;

					if (instruction.getCacheExternal() != _time) {
						long external = instruction.getStart();
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

					if (duration > available)
						duration = available;
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
						mix(source, duration, rate, volume);
					}
				}
			}

			if (_active.isEmpty() && _finished)
				break;

			if (available > 0) {
				for (int convert = 0; convert < output.length;) {
					int v = _mix[convert >>> 1];
					if (v > 32766)
						v = 32766;
					else if (v < -32766)
						v = -32766;
					output[convert++] = (byte) (v & 0xFF);
					output[convert++] = (byte) (v >>> 8);
				}

				int wrote = _target.write(output, 0, available);
				_time += wrote;
			}
		}

		if (_thread != null) {
			log.debug("End of composition, draining target..." );
			_target.drain();

			if (_timeSource) {
				_renderer.notifyFinished();
			}
		}
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
	 * @author Michael Stokes
	 */
	protected void mix(ShortBuffer source, int duration, int rate, int volume) {
		int base = source.position();

		for (int time = 0, output = 0; time < duration; time++) {
			int input = base + ((time * rate / 44100) << 1);

			_mix[output++] += (source.get(input) * volume) >> 7;
			_mix[output++] += (source.get(input + 1) * volume) >> 7;
		}
	}
}
