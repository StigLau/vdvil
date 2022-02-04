package no.bouvet.kpro.renderer;

import no.lau.vdvil.instruction.Instruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The TimeSourceRenderer class is a specialization of the AbstractRenderer
 * class. It does not actually do any rendering, but it exists as a fallback
 * mechanism to provide a time source if none of the functional renderer
 * implementations agree to provide a real, media-synchronized source. The
 * master OldRenderer will add a TimeSourceRenderer to its renderer list
 * automatically if this condition arises.
 * 
 * The TimeSourceRenderer will always agree to become the time source if asked.
 * It uses System.currentTimeMillis() to provide an unsynchronized time source.
 * 
 * @author Michael Stokes
 * @author Stig Lau
 */
public class TimeSourceRenderer extends AbstractRenderer implements Runnable {
	protected final static long SLEEP_TIME = 50;

	protected boolean _timeSource = false;
	protected Thread _thread;
	protected int _time;
    private final Logger log = LoggerFactory.getLogger(TimeSourceRenderer.class);

    /**
	 * Request that this TimeSourceRenderer become the time source. It will
	 * always agree.
	 * 
	 * @return true
	 */
	public boolean requestTimeSource() {
		return _timeSource = true;
	}

	/**
	 * Start this TimeSourceRenderer, at the given point in time.
	 * 
	 * @param time The time in samples when rendering begins
	 */
	public void start(int time) {
		stop();

		if (_timeSource) {
			_time = time;

			_thread = new Thread(this);
			_thread.start();
		}
	}

	/**
	 * Stop this TimeSourceRenderer.
	 */
    public void stop(Instruction instruction) {
        log.trace("Got stop instruction");
        if (_thread != null) {
			_thread.interrupt();
			_thread = null;
		}
	}

	/**
	 * Handle a rendering AbstractInstruction.
	 * 
	 * The TimeSourceRenderer does not actually render anything, so it ignores
	 * all Instructions. It does however respond to a null instruction by
	 * notifying the master OldRenderer that rendering has completed.
	 * 
	 * @param time
	 *            the current rendering time in samples
	 * @param instruction
	 *            the instruction that has occurred, or null
	 */
    public void notify(Instruction instruction, long time) {
        if (instruction == null) {
            if (_timeSource) {
                _renderer.notifyFinished();
            }
        }
    }

    public boolean isRendering() {
        return false;
        //To change body of implemented methods use File | Settings | File Templates.
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
			int elapsed = (int) ((System.currentTimeMillis() - started) * Instruction.RESOLUTION / 1000);

			_renderer.notifyTime(_time + elapsed);

			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
                log.trace("Stop interruption");
			}
		}
	}
}
