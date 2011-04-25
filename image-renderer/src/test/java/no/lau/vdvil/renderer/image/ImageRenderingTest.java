package no.lau.vdvil.renderer.image;

import no.vdvil.renderer.image.swinggui.ImageGUI;
import no.vdvil.renderer.image.ImageListener;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;

public class ImageRenderingTest {
    @Test
    public void testRenderingImage() throws InterruptedException, IOException {
        ImageListener listener = new ImageGUI(800, 600).getImageListener();
        Thread.sleep(100);
        listener.show(ClassLoader.getSystemResource("pink_teddy.jpg").openStream());
        Thread.sleep(200);
        listener.show(ClassLoader.getSystemResource("dj-teddy.jpg").openStream());
        Thread.sleep(200);
    }
}
