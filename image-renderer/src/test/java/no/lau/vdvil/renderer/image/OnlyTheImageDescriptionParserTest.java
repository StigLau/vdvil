package no.lau.vdvil.renderer.image;

import no.lau.vdvil.cache.SimpleCacheImpl;
import no.lau.vdvil.handler.persistence.DvlXML;
import no.lau.vdvil.handler.persistence.PartXML;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescription;
import no.vdvil.renderer.image.cacheinfrastructure.OnlyTheImageDescriptionParser;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import static org.junit.Assert.assertEquals;

public class OnlyTheImageDescriptionParserTest {
    URL imageUrl;
    URL compositionXmlUrl = getClass().getResource("/testCompositionWithImageDvls.xml");
    OnlyTheImageDescriptionParser parser = new OnlyTheImageDescriptionParser(new SimpleCacheImpl());

    @Before
    public void before() throws MalformedURLException {
        imageUrl = new URL("http://farm3.static.flickr.com/2095/2282261838_276a37d325_o_d.jpg");
    }

    @Test
    public void testFetchingImage() throws IOException {
        ImageDescription imageDescription = parser.parse(new PartXML("", -1, -1, new DvlXML("hello", imageUrl)));
        assertEquals(imageUrl, imageDescription.src);
    }

    @Test(expected = IOException.class)
    public void throwExceptionWhenNotImage() throws IOException {
        parser.parse(new PartXML("", -1, -1, new DvlXML("hello", compositionXmlUrl) ));
    }
}
