package no.bouvet.kpro.renderer;

/**
 * The TimeSourceRenderer class is a specialization of the AbstractRenderer
 * class. It does not actually do any rendering, but it exists as a fallback
 * mechanism to provide a time source if none of the functional renderer
 * implementations agree to provide a real, media-synchronized source. The
 * master Renderer will add a TimeSourceRenderer to its renderer list
 * automatically if this condition arises.
 * 
 * The TimeSourceRenderer will always agree to become the time source if asked.
 * It uses System.currentTimeMillis() to provide an unsynchronized time source.
 * 
 * @author Michael Stokes
 */
public class TimeSourceRenderer extends AbstractRenderer implements Runnable {
	protected final static long SLEEP_TIME = 50;

	protected boolean _timeSource = false;
	protected Thread _thread;
	protected int _time;

	/**
	 * Construct a new TimeSourceRenderer instance.
	 * 
	 * @author Michael Stokes
	 */
	public TimeSourceRenderer() {
	}

	/**
	 * Request that this TimeSourceRenderer become the time source. It will
	 * always agree.
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
	 * Start this TimeSourceRenderer, at the given point in time.
	 * 
	 * @param time
	 *            The time in samples when rendering begins
	 * @return true
	 * @author Michael Stokes
	 */
	@Override
	public boolean start(int time) {
		stop();

		if (_timeSource) {
			_time = time;

			_thread = new Thread(this);
			_thread.start();
		}

		return true;
	}

	/**
	 * Stop this TimeSourceRenderer.
	 * 
	 * @author Michael Stokes
	 */
	@Override
	public void stop() {
		if (_thread != null) {
			Thread thread = _thread;
			_thread = null;

			while (thread.isAlive()) {
				try {
					thread.join();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * Handle a rendering Instruction.
	 * 
	 * The TimeSourceRenderer does not actually render anything, so it ignores
	 * all Instructions. It does however respond to a null instruction by
	 * notifying the master Renderer that rendering has completed.
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
			if (_timeSource) {
				_renderer.notifyFinished();
			}
		}
	}

	/**
	 * This is the implementation of Runnable.run, and is the main thread
	 * procedure.
	 * 
	 * The TimeSourceRenderer uses System.currentTimeMillis() to provide an
	 * unsynchronized time source.
	 * 
	 * @author Michael Stokes
	 */
	public void run() {
		long started = System.currentTimeMillis();

		while (_thread != null) {
			int elapsed = (int) ((System.currentTimeMillis() - started)
					* Renderer.RATE / 1000);

			_renderer.notifyTime(_time + elapsed);

			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
			}
		}
	}
}
