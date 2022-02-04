package no.lau.vdvil.renderer.image;

import no.lau.IntegrationTest;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.instruction.ImageInstruction;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.image.ImageRenderer;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

@Category(IntegrationTest.class)
public class ImageRenderingTest {
    final Store store = Store.get();
    final FileRepresentation pinkTeddy = store.createKey(ClassLoader.getSystemResource("pink_teddy.jpg"), "5afcd12326717d727f694aba4d2e1055");
    final FileRepresentation dj_teddy = store.createKey(ClassLoader.getSystemResource("dj-teddy.jpg"), "b619f3035beed5a08525700cc4cd2be8");
    final FileRepresentation imageDesc = store.createKey(ClassLoader.getSystemResource("ImageDescription.html"), "2aa04fa306899ab577a9a94d357dbd0e");
    final FileRepresentation imageDesc2 = store.createKey(ClassLoader.getSystemResource("ImageDescription2.html"), "a91c9bdec322fe9d3eeb6338d88df35c");

    final ImageDescriptionXMLParser parser = new ImageDescriptionXMLParser();

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
