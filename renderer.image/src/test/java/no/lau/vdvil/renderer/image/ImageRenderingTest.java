package no.lau.vdvil.renderer.image;

import no.vdvil.renderer.image.swinggui.ImageGUI;
import no.vdvil.renderer.image.ImageListener;
import org.junit.Test;
import java.net.MalformedURLException;

public class ImageRenderingTest {
    @Test
    public void testRenderingImage() throws InterruptedException, MalformedURLException {
        ImageListener listener = new ImageGUI(800, 600).getImageListener();
        Thread.sleep(100);
        listener.show(ClassLoader.getSystemResource("pink_teddy.jpg"));
        Thread.sleep(200);
        listener.show(ClassLoader.getSystemResource("dj-teddy.jpg"));
        Thread.sleep(200);
    }
}
