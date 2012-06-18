package no.lau.vdvil.timing;

import no.lau.vdvil.renderer.MetronomeRenderer;
import no.lau.vdvil.renderer.Renderer;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TimingTest {

    @Test
    public void buildCase() {
        PushByHandClock clock = new PushByHandClock();
        UserTimer parentTimer = new UserTimer(clock);
        Renderer metronome = new MetronomeRenderer();


        int speed = 120;
        int perMinute = 60;
        int resolution = UserTimer.resolution * perMinute / speed;
        parentTimer.notifyEvery(metronome, resolution);
        //The clock has been started
        assertEquals(0, parentTimer.checkBeat());

        clock.currentTimeMillis = 1;
        assertEquals(0, parentTimer.checkBeat());

        clock.currentTimeMillis = 499;
        assertEquals(0, parentTimer.checkBeat());

        clock.currentTimeMillis = 501;
        assertEquals(500, parentTimer.checkBeat());
        assertEquals(500, parentTimer.lastBeat);
        clock.currentTimeMillis = 1; //skipping back in time requires rewinding the lastBeat!
        assertEquals(500, parentTimer.lastBeat);

        //TODO Make so that milliseconds are translated into beats by an intermidi timer!
    }
}

class UserTimer {

    private Renderer childTimer;
    private int notifyEvery;
    final static int resolution = 1000; // In microseconds
    private final PushByHandClock clock;
    long lastBeat = 0;


    public UserTimer(PushByHandClock clock) {
        this.clock = clock;
    }

    public void notifyEvery(Renderer childTimer, int notifyEvery) {
        this.childTimer = childTimer;
        this.notifyEvery = notifyEvery;
    }

    long checkBeat() {
        long lastTime = checkBeat(lastBeat);
        if(lastTime >= lastBeat) {
            lastBeat = lastTime;
            childTimer.notify(lastBeat);
        }
        return lastTime;
    }
    long checkBeat(long lastFoundBeat) {
        long currentTime = clock.getCurrentTimeMillis();
        if(lastFoundBeat + notifyEvery <= currentTime) {
            return checkBeat(lastFoundBeat + notifyEvery);
        } else
            return lastFoundBeat;
    }

}

class PushByHandClock {

    long currentTimeMillis = 0;

    public long getCurrentTimeMillis() {
        return currentTimeMillis;
    }
}

