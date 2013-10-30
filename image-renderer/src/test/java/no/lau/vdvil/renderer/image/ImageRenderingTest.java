package no.lau.vdvil.renderer.image;

import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.instruction.ImageInstruction;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
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
    Store store = Store.get();

    ImageDescriptionXMLParser parser = new ImageDescriptionXMLParser(store);

    @Test
    public void testRenderingImage() throws InterruptedException, IOException {
        ImageRenderer renderer = new ImageRenderer(800, 600);
        renderer.notify(ImageInstruction.create(new MasterBeatPattern(new Interval(-0, -0), -0F), store.cache(pinkTeddy)), 0);
        renderer.start(0);
        Thread.sleep(400);
        renderer.notify(ImageInstruction.create(new MasterBeatPattern(new Interval(-0, -0), -0F), store.cache(dj_teddy)), 0);
        Thread.sleep(1000);
        renderer.stop();
    }

    @Test
    public void newInfrastructureTest() throws Exception{
        ImageRenderer renderer = new ImageRenderer(800, 600);

        renderer.notify(parser.parse(PartXML.create(imageDesc)).asInstruction(120F), 0);
        renderer.start(0);
        Thread.sleep(400);
        renderer.notify(parser.parse(PartXML.create(imageDesc2)).asInstruction(120F), 0);
        Thread.sleep(2000);
        renderer.stop();
    }
}
