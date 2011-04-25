package no.lau.vdvil.renderer.image;

import no.vdvil.renderer.image.ImageGUI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class ImageRenderingTest {
    @Test
    public void testRenderingImage() throws InterruptedException, MalformedURLException {
        ImageGUI imageGUI = new ImageGUI(800, 600);
        Thread.sleep(100);
        imageGUI.imagePanel.show(ClassLoader.getSystemResource("pink_teddy.jpg"));
        Thread.sleep(200);
        imageGUI.imagePanel.show(ClassLoader.getSystemResource("dj-teddy.jpg"));
        Thread.sleep(200);
    }
}
