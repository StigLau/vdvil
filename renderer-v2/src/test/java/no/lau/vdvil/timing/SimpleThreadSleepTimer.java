package no.lau.vdvil.timing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleThreadSleepTimer implements Timer {

    private final long waitFor;
    private final Conductor conductor;
    private final int start;
    private final int length;
    static final Logger log = LoggerFactory.getLogger(SimpleThreadSleepTimer.class);

    public SimpleThreadSleepTimer(int speed, int divider, Conductor conductor, int start, int length) {
        this.conductor = conductor;
        this.start = start;
        this.length = length;
        waitFor = 1000 * divider / speed;
    }

    public void play() {
        for (int beat = start; beat < start+length; beat++) {
            conductor.notify(beat);
            try {
                Thread.sleep(waitFor);
            } catch (InterruptedException ie) {
                log.error("Abrupt awakening", ie);
            }
        }
        log.info("Timer finished");
    }
}
