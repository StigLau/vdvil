package no.lau.vdvil.renderer.image;

import no.vdvil.renderer.image.ImageInstruction;
import no.vdvil.renderer.image.ImageRenderer;
import org.junit.Test;
import java.io.IOException;

public class ImageRenderingTest {
    @Test
    public void testRenderingImage() throws InterruptedException, IOException {
        ImageRenderer renderer = new ImageRenderer(800, 600);
        renderer.handleInstruction(0, new ImageInstruction(-0, -0, -0F, ClassLoader.getSystemResource("pink_teddy.jpg").openStream()));
        renderer.start(0);
        Thread.sleep(400);
        renderer.handleInstruction(0, new ImageInstruction(-0, -0, -0F, ClassLoader.getSystemResource("dj-teddy.jpg").openStream()));
        Thread.sleep(1000);
        renderer.stop();
    }
}
