package no.lau.vdvil.timing;

import no.lau.vdvil.renderer.MetronomeInstruction;
import no.lau.vdvil.renderer.MetronomeRenderer;
import no.lau.vdvil.renderer.Renderer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimingTest {

    @Test
    public void buildCase() {
        PushByHandClock clock = new PushByHandClock();
        ResolutionTimer parentTimer = new ResolutionTimer(clock);
        Renderer metronome = new MetronomeRenderer();
        BeatRenderer beatRenderer = new BeatRenderer();
        beatRenderer.addInstruction(new ResolutionInstruction(0, 64, 120, 60));
        beatRenderer.setParent(parentTimer);
        beatRenderer.addRenderer(metronome);

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

    static int calculateResolution(int speed, int perMinute) {
        return ResolutionTimer.resolution * perMinute / speed;
    }

    @Test
    public void testBeatCalculation() {
        PushByHandClock clock = new PushByHandClock();
        ResolutionTimer parentTimer = new ResolutionTimer(clock);
        BeatRenderer beatRenderer = new BeatRenderer();
        beatRenderer.addInstruction(new ResolutionInstruction(0, 64, 120, 60));
        beatRenderer.setParent(parentTimer);

        MetronomeRenderer metronome = new MetronomeRenderer();
        metronome.addInstruction(new MetronomeInstruction(0, 128));

        beatRenderer.addRenderer(metronome);


        parentTimer.checkBeat();
        clock.currentTimeMillis = 500;
        parentTimer.checkBeat();
        clock.currentTimeMillis = 1000;
        parentTimer.checkBeat();
        clock.currentTimeMillis = 1500;
        parentTimer.checkBeat();
        clock.currentTimeMillis = 2000;
        parentTimer.checkBeat();
    }


    @Test
    public void testWithRunningClock() throws InterruptedException {
        Clock clock = new SystemClock();
        //Set playback to start in 2 seconds
        long origo = clock.getCurrentTimeMillis();
        final RunnableResolutionTimer timer = new RunnableResolutionTimer(clock, origo);
        BeatRenderer beatRenderer = new BeatRenderer() {
            {
                addInstruction(new ResolutionInstruction(0, 64, 120, 60));
                //Set beatrenderer to clock + 5 sec
                setParent(timer);
                MetronomeRenderer metronome = new MetronomeRenderer();
                metronome.addInstruction(new MetronomeInstruction(0, 128));
                addRenderer(metronome);
            }
        };

        //new Thread(timer).start();
        timer.run();
        Thread.sleep(20000);

    }
}

class PushByHandClock implements Clock {

    long currentTimeMillis = 0;

    public long getCurrentTimeMillis() {
        return currentTimeMillis;
    }
}

class SystemClock implements Clock {
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}

interface Clock {
    long getCurrentTimeMillis();
}

