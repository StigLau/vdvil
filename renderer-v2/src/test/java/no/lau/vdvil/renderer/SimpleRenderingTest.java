package no.lau.vdvil.renderer;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.control.Conductor;
import no.lau.vdvil.timing.*;
import org.junit.Test;

public class SimpleRenderingTest {

    @Test
    public void imageAndMetronomeTest() throws InterruptedException {
        Clock clock = new SystemClock();
        long origo = clock.getCurrentTimeMillis();
        final RunnableResolutionTimer timer = new RunnableResolutionTimer(clock, origo);
        Conductor conductor = new Conductor(timer, 120);

        MetronomeRenderer metronome = new MetronomeRenderer(0, 8);
        conductor.addInstruction(metronome, metronome.instructions());
        conductor.addInstruction(new ImageRenderer(), new ImageInstruction[]{
                        new ImageInstruction("Image/jpg", 0, 4, FileRepresentation.NULL),
                        new ImageInstruction("Image/jpg", 4, 4, FileRepresentation.NULL),
                /*
                        new ImageInstruction("Image/jpg", 8, 4, FileRepresentation.NULL),
                        new ImageInstruction("Image/jpg", 12, 4, FileRepresentation.NULL),
                        new ImageInstruction("Image/jpg", 16, 8, FileRepresentation.NULL),
                        new ImageInstruction("Image/jpg", 32, 4, FileRepresentation.NULL),
                        new ImageInstruction("Image/jpg", 24, 8, FileRepresentation.NULL),
                        */
                });
        timer.play();
        while(conductor.isPlaying()) {
            Thread.sleep(500);
        }
    }
}
