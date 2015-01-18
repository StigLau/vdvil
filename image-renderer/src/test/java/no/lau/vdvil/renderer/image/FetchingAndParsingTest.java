package no.lau.vdvil.renderer.image;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.instruction.ImageInstruction;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescription;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import org.junit.Test;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FetchingAndParsingTest {

    Store store = Store.get();

    @Test
    public void fetchingAndParsing() throws Exception {
        int start = 0;
        int duration = 16;
        float bpm = 130F;

        FileRepresentation imageDescriptionURL = store.createKey(ClassLoader.getSystemResource("ImageDescription.html"), "ed2b9545a45abab5b2a483a8d6a8b1cb");
        ImageDescription imageDescription = new ImageDescriptionXMLParser().parse(PartXML.create(imageDescriptionURL));

        ImageInstruction imageInstruction = ImageInstruction.create(new MasterBeatPattern(new Interval(start, duration), bpm), imageDescription.fileRepresentation());
        //Cache
        assertNotNull(store.cache(imageDescription.fileRepresentation()));
    }

    @Test
    public void testFileCacheAccepts() throws MalformedURLException {
        assertTrue(store.accepts(new URL("http://yes.com")));
        assertTrue(store.accepts(new URL("file://localhost/Users")));
        assertFalse(store.accepts(new URL("ftp://no.com")));
    }
}


