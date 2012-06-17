package no.lau.vdvil.timing;

import no.lau.vdvil.renderer.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleThreadSleepTimer implements no.lau.vdvil.timing.Timer {

    private final long waitFor;
    static final Logger log = LoggerFactory.getLogger(SimpleThreadSleepTimer.class);
    private Renderer renderer;
    private final int start;
    private final int length;

    public SimpleThreadSleepTimer(int speed, int divider, int start, int length) {
        this.start = start;
        this.length = length;
        waitFor = 1000 * divider / speed;
    }

    public void play() {
        for (int beat = start; beat < start+length; beat++) {
            renderer.notify(beat);
            try {
                Thread.sleep(waitFor);
            } catch (InterruptedException ie) {
                log.error("Abrupt awakening", ie);
            }
        }
        log.info("Timer finished");
    }

    public void setMasterRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
}
