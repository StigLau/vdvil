package no.lau.vdvil.timing;

import no.lau.vdvil.renderer.MetronomeRenderer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class TimingTest {
    static final Logger log = LoggerFactory.getLogger(TimingTest.class);

    @Test
    public void buildCase() {
        Conductor conductor = new Conductor();
        ResolutionTimer timer = new ResolutionTimer(null);
        new BeatTimeConverter(conductor, timer, 120, 60);
        MetronomeRenderer metronome = new MetronomeRenderer(0, 64);
        conductor.addInstruction(metronome, metronome.instructions());

        //The clock has been started
        assertEquals(0, (long) timer.updateSlider(0));
        assertEquals(500, timer.timeSlider);

        assertEquals(null, timer.updateSlider(1));
        assertEquals(500, timer.timeSlider);

        assertEquals(null, timer.updateSlider(499));
        assertEquals(500, timer.timeSlider);

        assertEquals(500, (long) timer.updateSlider(501));
        assertEquals(1000, timer.timeSlider);

        assertEquals(null, timer.updateSlider(1));//skipping back in time requires rewinding the lastBeat!
        assertEquals(1000, timer.timeSlider);
    }

    /**
     * Should print out Beat 0 - Beat 4
     */
    @Test
    public void testBeatCalculation() {
        log.info("Programatically pushing time and Metronome to print Beat 0 - Beat 4");
        Conductor conductor = new Conductor();
        PushByHandClock clock = new PushByHandClock();
        ResolutionTimer parentTimer = new ResolutionTimer(clock);
        new BeatTimeConverter(conductor, parentTimer, 120, 60);


        MetronomeRenderer metronome = new MetronomeRenderer(0, 128);
        conductor.addInstruction(metronome, metronome.instructions());

        parentTimer.checkTimeAndNotify();
        clock.currentTimeMillis = 500;
        parentTimer.checkTimeAndNotify();
        clock.currentTimeMillis = 1000;
        parentTimer.checkTimeAndNotify();
        clock.currentTimeMillis = 1500;
        parentTimer.checkTimeAndNotify();
        clock.currentTimeMillis = 2000;
        parentTimer.checkTimeAndNotify();
    }

    @Test
    public void printFourBeatsWithSimpleSystemClock() throws InterruptedException {
        log.info("Printing out four beats on time:");
        final Conductor conductor = new Conductor();
        Clock clock = new SystemClock();
        //Set playback to start in 2 seconds
        long origo = clock.getCurrentTimeMillis();
        final RunnableResolutionTimer timer = new RunnableResolutionTimer(clock, origo);
        new BeatTimeConverter(conductor, timer, 120, 60);
        MetronomeRenderer metronome = new MetronomeRenderer(0, 64);
        conductor.addInstruction(metronome, metronome.instructions());
        new Thread(timer).start();
        Thread.sleep(2200);
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

