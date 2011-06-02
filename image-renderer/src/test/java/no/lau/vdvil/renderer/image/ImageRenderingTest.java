package no.lau.vdvil.renderer.image;

import no.lau.vdvil.cache.SimpleVdvilCache;
import no.vdvil.renderer.image.ImageInstruction;
import no.vdvil.renderer.image.ImageRenderer;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import no.lau.vdvil.handler.persistence.SimpleFileCache;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;

public class ImageRenderingTest {
    URL pinkTeddy = ClassLoader.getSystemResource("pink_teddy.jpg");
    URL dj_teddy = ClassLoader.getSystemResource("dj-teddy.jpg");
    URL imageDesc = ClassLoader.getSystemResource("ImageDescription.html");
    URL imageDesc2 = ClassLoader.getSystemResource("ImageDescription2.html");

    SimpleVdvilCache cache = new SimpleFileCache();
    ImageDescriptionXMLParser parser = new ImageDescriptionXMLParser(cache);

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

        renderer.handleInstruction(0, parser.parse("", imageDesc).asInstruction(120F));
        renderer.start(0);
        Thread.sleep(400);
        renderer.handleInstruction(0, parser.parse("", imageDesc2).asInstruction(120F));
        Thread.sleep(2000);
        renderer.stop();
    }
}
