package no.bouvet.kpro.renderer;

import no.lau.vdvil.renderer.SuperRenderer;
import no.lau.vdvil.renderer.TimeSource;
import no.lau.vdvil.timing.Time;

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
public class TimeSourceRenderer extends SuperRenderer implements TimeSource, Runnable {
	protected final static long SLEEP_TIME = 50;

	protected boolean _timeSource = false;
	protected Thread _thread;
	protected int _time;
    Renderer renderer;

	/**
	 * Request that this TimeSourceRenderer become the time source. It will
	 * always agree.
	 * 
	 * @return true
	 */
	@Override
	public boolean requestTimeSource() {
		_timeSource = true;
		return true;
	}

    @Override
    public void start() {
        _time = 0;
        _thread = new Thread(this);
        _thread.start();
    }

	/**
	 * Stop this TimeSourceRenderer.
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

    @Override
    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    protected boolean passesFilter(Instruction instruction) {
        return false;  
    }

    @Override
    public boolean isRendering() { return true; }

    @Override
    public void ping(Time time) { }

    @Override
    public void stop(Instruction instruction) {
    }

    /**
	 * This is the implementation of Runnable.run, and is the main thread
	 * procedure.
	 * 
	 * The TimeSourceRenderer uses System.currentTimeMillis() to provide an
	 * unsynchronized time source.
	 */
	public void run() {
		long started = System.currentTimeMillis();

		while (_thread != null) {
			int elapsed = (int) ((System.currentTimeMillis() - started)
					* Renderer.RATE / 1000);

			renderer.notifyTime(new Time(_time + elapsed));

			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
			}
		}
	}
}
