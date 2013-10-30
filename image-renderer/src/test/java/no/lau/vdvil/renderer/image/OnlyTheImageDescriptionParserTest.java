package no.lau.vdvil.renderer.image;

import no.lau.vdvil.handler.persistence.DvlXML;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.timing.Interval;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescription;
import no.vdvil.renderer.image.cacheinfrastructure.OnlyTheImageDescriptionParser;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;
import static org.junit.Assert.assertEquals;

public class OnlyTheImageDescriptionParserTest {
    String imagePath = "http://farm3.static.flickr.com/2095/2282261838_276a37d325_o_d.jpg";
    URL compositionXmlUrl = ClassLoader.getSystemResource("testCompositionWithImageDvls.xml");
    OnlyTheImageDescriptionParser parser = new OnlyTheImageDescriptionParser();

    @Test
    public void testFetchingImage() throws IOException {
        URL imageUrl = new URL(imagePath);
        ImageDescription imageDescription = parser.parse(new PartXML("", new Interval(-1, 0), new DvlXML("hello", imageUrl)));
        assertEquals(imageUrl, imageDescription.fileRepresentation().remoteAddress());
    }

    @Test(expected = IOException.class)
    public void throwExceptionWhenNotImage() throws IOException {
        parser.parse(new PartXML("", new Interval(-1, 0), new DvlXML("hello", compositionXmlUrl) ));
    }
}
