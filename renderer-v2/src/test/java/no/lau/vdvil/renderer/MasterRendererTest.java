package no.lau.vdvil.renderer;

import org.junit.Test;

public class MasterRendererTest {

    @Test
    public void testCreatingAMasterRenderer() throws InterruptedException {
        int beatsPerSecond = 120;
        int divider = 60;
        SimpleTestTimer timer = new SimpleTestTimer(beatsPerSecond, divider);


        ImageRenderer imageRenderer = new ImageRenderer(timer);
        imageRenderer.addInstruction(new ImageInstruction("http://vg.no/happy1.jpg", "Image/jpg", 0, 4));
        imageRenderer.addInstruction(new ImageInstruction("http://vg.no/happy2.jpg", "Image/jpg", 4, 4));
        imageRenderer.addInstruction(new ImageInstruction("http://vg.no/happy3.jpg", "Image/jpg", 8, 4));
        imageRenderer.addInstruction(new ImageInstruction("http://vg.no/happy4.jpg", "Image/jpg", 12, 4));
        imageRenderer.addInstruction(new ImageInstruction("http://vg.no/sad1.png", "Image/png", 16, 8));
        imageRenderer.addInstruction(new ImageInstruction("http://vg.no/sad2.png", "Image/png", 24, 8));
        imageRenderer.addInstruction(new ImageInstruction("http://vg.no/woot.png", "Image/png", 32, 4));

        timer.play(0, 36);
    }
}
