package no.lau.vdvil.renderer.image;

import no.lau.vdvil.cache.SimpleCacheImpl;
import no.lau.vdvil.cache.SimpleVdvilCache;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.lau.vdvil.timing.Time;
import no.vdvil.renderer.image.ImageInstruction;
import no.vdvil.renderer.image.ImageRenderer;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;

public class ImageRenderingTest {
    URL pinkTeddy = ClassLoader.getSystemResource("pink_teddy.jpg");
    URL dj_teddy = ClassLoader.getSystemResource("dj-teddy.jpg");
    URL imageDesc = ClassLoader.getSystemResource("ImageDescription.html");
    URL imageDesc2 = ClassLoader.getSystemResource("ImageDescription2.html");

    SimpleVdvilCache cache = new SimpleCacheImpl();
    ImageDescriptionXMLParser parser = new ImageDescriptionXMLParser(cache);

    @Test
    public void testRenderingImage() throws InterruptedException, IOException {
        ImageRenderer renderer = new ImageRenderer(800, 600, cache);
        renderer.addInstruction(ImageInstruction.create(new MasterBeatPattern(new Interval(-0, -0), -0F), pinkTeddy, pinkTeddy.openStream()));
        renderer.ping(new Time(0));
        Thread.sleep(400);
        renderer.addInstruction(ImageInstruction.create(new MasterBeatPattern(new Interval(-0, -0), -0F), dj_teddy, dj_teddy.openStream()));
        Thread.sleep(1000);
        renderer.stop();
    }

    @Test
    public void newInfrastructureTest() throws Exception{
        ImageRenderer renderer = new ImageRenderer(800, 600, cache);

        renderer.addInstruction(parser.parse(PartXML.create(imageDesc)).asInstruction(120F));
        renderer.ping(new Time(0));
        Thread.sleep(400);
        renderer.addInstruction(parser.parse(PartXML.create(imageDesc2)).asInstruction(120F));
        Thread.sleep(2000);
        renderer.stop();
    }
}
