package no.lau.vdvil.renderer.image;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.instruction.ImageInstruction;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescription;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import org.junit.jupiter.api.Test;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class FetchingAndParsingTest {

    final Store store = Store.get();

    @Test
    public void fetchingAndParsing() throws Exception {
        int start = 0;
        int duration = 16;
        float bpm = 130F;

        FileRepresentation imageDescriptionURL = store.createKey(ClassLoader.getSystemResource("ImageDescription.html"), "2aa04fa306899ab577a9a94d357dbd0e");
        ImageDescription imageDescription = new ImageDescriptionXMLParser().parse(PartXML.create(imageDescriptionURL));

        ImageInstruction imageInstruction = ImageInstruction.create(new MasterBeatPattern(new Interval(start, duration), bpm), imageDescription.fileRepresentation());
        //Cache
        assertNotNull(store.cache(imageDescription.fileRepresentation()));
    }

    @Test
    public void testFileCacheAccepts() throws MalformedURLException {
        assertTrue(store.accepts(new URL("https://yes.com")));
        assertTrue(store.accepts(new URL("file://localhost/Users")));
        assertFalse(store.accepts(new URL("ftp://no.com")));
    }
}


