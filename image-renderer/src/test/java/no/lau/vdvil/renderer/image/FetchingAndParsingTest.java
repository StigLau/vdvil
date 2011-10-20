package no.lau.vdvil.renderer.image;

import no.lau.vdvil.cache.SimpleCacheImpl;
import no.lau.vdvil.cache.SimpleVdvilCache;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.image.ImageInstruction;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescription;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import org.junit.Test;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FetchingAndParsingTest {

    SimpleVdvilCache testCache = new SimpleCacheImpl();

    @Test
    public void fetchingAndParsing() throws Exception {
        int start = 0;
        int duration = 16;
        float bpm = 130F;

        URL imageDescriptionURL = getClass().getResource("/ImageDescription.html");
        ImageDescription imageDescription = new ImageDescriptionXMLParser(testCache).parse(PartXML.create(imageDescriptionURL));

        ImageInstruction imageInstruction = ImageInstruction.create(new MasterBeatPattern(new Interval(start, duration), bpm), imageDescription.src, imageDescription.src.openStream());
        //Cache
        assertNotNull(testCache.fetchAsStream(imageDescription.src));
    }

    @Test
    public void testFileCacheAccepts() throws MalformedURLException {
        assertTrue(testCache.accepts(new URL("http://yes.com")));
        assertTrue(testCache.accepts(new URL("file://localhost/Users")));
        assertFalse(testCache.accepts(new URL("ftp://no.com")));
    }
}


