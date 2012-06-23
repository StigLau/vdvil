package no.lau.vdvil.timing;

import no.lau.vdvil.renderer.MetronomeInstruction;
import no.lau.vdvil.renderer.MetronomeRenderer;
import no.lau.vdvil.renderer.Renderer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class TimingTest {
    static final Logger log = LoggerFactory.getLogger(TimingTest.class);

    @Test
    public void buildCase() {
        ResolutionTimer parentTimer = new ResolutionTimer(null);
        Renderer metronome = new MetronomeRenderer();
        Conductor conductor = new Conductor();
        conductor.addInstruction(new ResolutionInstruction(0, 64, 120, 60));
        conductor.setParent(parentTimer);
        conductor.addRenderer(metronome);

        //The clock has been started
        parentTimer.updateSlider(0);
        assertEquals(0, parentTimer.timeSlider);

        parentTimer.updateSlider(1);
        assertEquals(0, parentTimer.timeSlider);

        parentTimer.updateSlider(499);
        assertEquals(0, parentTimer.timeSlider);

        parentTimer.updateSlider(501);
        assertEquals(500, parentTimer.timeSlider);

        parentTimer.updateSlider(1);//skipping back in time requires rewinding the lastBeat!
        assertEquals(500, parentTimer.timeSlider);
    }

    /**
     * Should print out Beat 0 - Beat 4
     */
    @Test
    public void testBeatCalculation() {
        log.info("Programatically pushing time and Metronome to print Beat 0 - Beat 4");
        PushByHandClock clock = new PushByHandClock();
        ResolutionTimer parentTimer = new ResolutionTimer(clock);
        Conductor conductor = new Conductor();
        conductor.addInstruction(new ResolutionInstruction(0, 64, 120, 60));
        conductor.setParent(parentTimer);

        MetronomeRenderer metronome = new MetronomeRenderer();
        metronome.addInstruction(new MetronomeInstruction(0, 128));

        conductor.addRenderer(metronome);

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
        Clock clock = new SystemClock();
        //Set playback to start in 2 seconds
        long origo = clock.getCurrentTimeMillis();
        final RunnableResolutionTimer timer = new RunnableResolutionTimer(clock, origo);
        new Conductor() {
            {
                addInstruction(new ResolutionInstruction(0, 64, 120, 60));
                //Set beatrenderer to clock + 5 sec
                setParent(timer);
                MetronomeRenderer metronome = new MetronomeRenderer();
                metronome.addInstruction(new MetronomeInstruction(0, 4));
                addRenderer(metronome);
            }
        };

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

