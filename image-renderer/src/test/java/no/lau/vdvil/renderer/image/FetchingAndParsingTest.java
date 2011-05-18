package no.lau.vdvil.renderer.image;

import no.lau.vdvil.cache.SimpleVdvilCache;
import no.vdvil.renderer.image.ImageInstruction;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescription;
import no.vdvil.renderer.image.cacheinfrastructure.SimpleFileCache;
import org.junit.Test;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FetchingAndParsingTest {

    SimpleVdvilCache testCache = new SimpleFileCache();

    @Test
    public void fetchingAndParsing() throws Exception {
        int start = 0;
        int end = 16;
        float bpm = 130F;

        URL imageDescriptionURL = getClass().getResource("/ImageDescription.html");
        InputStream imageDescriptionInputStream = testCache.fetchAsStream(imageDescriptionURL);


        ImageDescription imageDescription = ImageDescription.parse(imageDescriptionInputStream);

        ImageInstruction imageInstruction = new ImageInstruction(start, end, bpm, imageDescription.src);
        //Cache
        InputStream imageStream = testCache.fetchAsStream(imageDescription.src);
    }

    @Test
    public void testFileCacheAccepts() throws MalformedURLException {
        assertTrue(new SimpleFileCache().accepts(new URL("http://yes.com")));
        assertTrue(new SimpleFileCache().accepts(new URL("file://localhost/Users")));
        assertFalse(new SimpleFileCache().accepts(new URL("ftp://no.com")));
    }
}


