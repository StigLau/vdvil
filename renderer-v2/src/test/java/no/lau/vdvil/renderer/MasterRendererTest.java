package no.lau.vdvil.renderer;

import no.lau.vdvil.timing.SimpleThreadSleepTimer;
import no.lau.vdvil.timing.Timer;
import org.junit.Test;

public class MasterRendererTest {

    @Test
    public void imageAndMetronomeTest() throws InterruptedException {
        int beatsPerSecond = 120;
        int divider = 60;
        SimpleThreadSleepTimer timer = new SimpleThreadSleepTimer(beatsPerSecond, divider, 0, 36);

        MasterRenderer masterRenderer = new MasterRenderer();
        timer.setMasterRenderer(masterRenderer);
        ImageRenderer imageRenderer = new ImageRenderer(masterRenderer);
        masterRenderer.addRenderer(imageRenderer);

        MetronomeRenderer metronomeRenderer = new MetronomeRenderer(masterRenderer);
        metronomeRenderer.addInstruction(new MetronomeInstruction(0, 32));
        masterRenderer.addRenderer(metronomeRenderer);


        imageRenderer.addInstruction(new ImageInstruction("http://vg.no/happy1.jpg", "Image/jpg", 0, 4));
        imageRenderer.addInstruction(new ImageInstruction("http://vg.no/happy2.jpg", "Image/jpg", 4, 4));
        imageRenderer.addInstruction(new ImageInstruction("http://vg.no/happy3.jpg", "Image/jpg", 8, 4));
        imageRenderer.addInstruction(new ImageInstruction("http://vg.no/happy4.jpg", "Image/jpg", 12, 4));
        imageRenderer.addInstruction(new ImageInstruction("http://vg.no/sad1.png", "Image/png", 16, 8));
        imageRenderer.addInstruction(new ImageInstruction("http://vg.no/sad2.png", "Image/png", 24, 8));
        imageRenderer.addInstruction(new ImageInstruction("http://vg.no/woot.png", "Image/png", 32, 4));

        timer.play();
    }

    @Test
    public void metronomeTest() {
        new SimpleThreadSleepTimer(120, 60, 0, 8) {
            {
                MasterRenderer masterRenderer = new MasterRenderer();
                setMasterRenderer(masterRenderer);
                MetronomeRenderer metronomeRenderer = new MetronomeRenderer(masterRenderer);
                metronomeRenderer.addInstruction(new MetronomeInstruction(0, 64));
                masterRenderer.addRenderer(metronomeRenderer);
            }

        }.play();
    }
}