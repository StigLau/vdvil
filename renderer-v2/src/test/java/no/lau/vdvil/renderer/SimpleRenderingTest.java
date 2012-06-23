package no.lau.vdvil.renderer;

import no.lau.vdvil.timing.Conductor;
import no.lau.vdvil.timing.SimpleThreadSleepTimer;
import org.junit.Test;

public class SimpleRenderingTest {

    @Test
    public void imageAndMetronomeTest() throws InterruptedException {
        Conductor conductor = new Conductor();
        int beatsPerSecond = 120;
        int divider = 60;
        SimpleThreadSleepTimer timer = new SimpleThreadSleepTimer(beatsPerSecond, divider, conductor, 0, 36);

        MetronomeRenderer metronome = new MetronomeRenderer(0, 32);
        conductor.addInstruction(metronome, metronome.instructions());
        conductor.addInstruction(new ImageRenderer(), new ImageInstruction[]{
                        new ImageInstruction("http://vg.no/happy1.jpg", "Image/jpg", 0, 4),
                        new ImageInstruction("http://vg.no/happy2.jpg", "Image/jpg", 4, 4),
                        new ImageInstruction("http://vg.no/happy3.jpg", "Image/jpg", 8, 4),
                        new ImageInstruction("http://vg.no/happy4.jpg", "Image/jpg", 12, 4),
                        new ImageInstruction("http://vg.no/sad1.png", "Image/png", 16, 8),
                        new ImageInstruction("http://vg.no/woot.png", "Image/png", 32, 4),
                        new ImageInstruction("http://vg.no/sad2.png", "Image/png", 24, 8)
                });
        timer.play();
    }

    @Test
    public void metronomeTest() {
        Conductor conductor = new Conductor();
        MetronomeRenderer metronome = new MetronomeRenderer(0, 8);
        conductor.addInstruction(metronome, metronome.instructions());

        new SimpleThreadSleepTimer(120, 60, conductor, 0, 8).play();
    }
}
