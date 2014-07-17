package no.lau.vdvil.renderer.image;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.instruction.ImageInstruction;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.image.ImageRenderer;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import org.junit.Test;
import java.io.IOException;

public class ImageRenderingTest {
    Store store = Store.get();
    FileRepresentation pinkTeddy = store.createKey(ClassLoader.getSystemResource("pink_teddy.jpg"), "5afcd12326717d727f694aba4d2e1055");
    FileRepresentation dj_teddy = store.createKey(ClassLoader.getSystemResource("dj-teddy.jpg"), "b619f3035beed5a08525700cc4cd2be8");
    FileRepresentation imageDesc = store.createKey(ClassLoader.getSystemResource("ImageDescription.html"), "ed2b9545a45abab5b2a483a8d6a8b1cb");
    FileRepresentation imageDesc2 = store.createKey(ClassLoader.getSystemResource("ImageDescription2.html"), "c456e0b04134aa7f7997b90cd3da1a62");

    ImageDescriptionXMLParser parser = new ImageDescriptionXMLParser();

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
