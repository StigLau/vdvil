package no.lau.vdvil.timing;

import no.lau.vdvil.control.Conductor;
import no.lau.vdvil.renderer.MetronomeRenderer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TimingTest {
    static final Logger log = LoggerFactory.getLogger(TimingTest.class);

    @Test
    public void buildCase() {
        MetronomeRenderer metronome = new MetronomeRenderer(0, 64);
        ResolutionTimer timer = new ResolutionTimer(null, 0);
        Conductor conductor = new Conductor(timer, 120);
        conductor.addInstruction(metronome, metronome.instructions());

        //The clock has been started
        assertEquals(0, (long) timer.updateSlider(0));
        assertEquals(500, timer.timeSlider);

        assertNull(timer.updateSlider(1));
        assertEquals(500, timer.timeSlider);

        assertNull(timer.updateSlider(499));
        assertEquals(500, timer.timeSlider);

        assertEquals(500, (long) timer.updateSlider(501));
        assertEquals(1000, timer.timeSlider);

        assertNull(timer.updateSlider(1));//skipping back in time requires rewinding the lastBeat!
        assertEquals(1000, timer.timeSlider);
    }

    /**
     * Should print out Beat 0 - Beat 4
     */
    @Test
    public void testBeatCalculation() {
        log.info("Programatically pushing time and Metronome to print Beat 0 - Beat 4");
        PushByHandClock clock = new PushByHandClock();
        ResolutionTimer parentTimer = new ResolutionTimer(clock, 0);
        Conductor conductor = new Conductor(parentTimer,  120);

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
        Clock clock = new SystemClock();
        long origo = clock.getCurrentTimeMillis();
        final RunnableResolutionTimer timer = new RunnableResolutionTimer(clock, origo);
        final Conductor conductor = new Conductor(timer, 120);
        MetronomeRenderer metronome = new MetronomeRenderer(0, 4);
        conductor.addInstruction(metronome, metronome.instructions());
        timer.play();
        while(conductor.isPlaying()) {
            Thread.sleep(500);
        }
    }
}

class PushByHandClock implements Clock {

    long currentTimeMillis = 0;

    public long getCurrentTimeMillis() {
        return currentTimeMillis;
    }
}

