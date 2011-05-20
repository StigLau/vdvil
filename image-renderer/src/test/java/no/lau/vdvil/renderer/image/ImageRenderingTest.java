package no.lau.vdvil.renderer.image;

import no.lau.vdvil.cache.SimpleVdvilCache;
import no.vdvil.renderer.image.ImageInstruction;
import no.vdvil.renderer.image.ImageRenderer;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescription;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import no.vdvil.renderer.image.cacheinfrastructure.SimpleFileCache;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;

public class ImageRenderingTest {
    SimpleVdvilCache cache = new SimpleFileCache();
    URL pinkTeddy = ClassLoader.getSystemResource("pink_teddy.jpg");
    URL dj_teddy = ClassLoader.getSystemResource("dj-teddy.jpg");
    URL imageDesc = ClassLoader.getSystemResource("ImageDescription.html");
    URL imageDesc2 = ClassLoader.getSystemResource("ImageDescription2.html");


    @Test
    public void testRenderingImage() throws InterruptedException, IOException {
        ImageRenderer renderer = new ImageRenderer(800, 600, cache);
        renderer.handleInstruction(0, new ImageInstruction(-0, -0, -0F, pinkTeddy));
        renderer.start(0);
        Thread.sleep(400);
        renderer.handleInstruction(0, new ImageInstruction(-0, -0, -0F, dj_teddy));
        Thread.sleep(1000);
        renderer.stop();
    }



    @Test
    public void newInfrastructureTest() throws Exception{
        ImageRenderer renderer = new ImageRenderer(800, 600, cache);

        renderer.handleInstruction(0, new ImageDescriptionXMLParser().parse(cache.fetchAsStream(imageDesc)).asInstruction(-0, -0, -0F));
        renderer.start(0);
        Thread.sleep(400);
        renderer.handleInstruction(0, new ImageDescriptionXMLParser().parse(cache.fetchAsStream(imageDesc2)).asInstruction(-0, -0, -0F));
        Thread.sleep(2000);
        renderer.stop();
    }
}
