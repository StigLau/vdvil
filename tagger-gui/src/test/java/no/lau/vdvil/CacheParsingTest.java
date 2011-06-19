package no.lau.vdvil;

import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.lau.tagger.model.SimpleSong;
import no.vdvil.renderer.audio.TestMp3s;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CacheParsingTest {
    VdvilHttpCache cache = VdvilHttpCache.create();
    URL dvlUrl = TestMp3s.returningDvl;

    @Test
    public void parsingTheXmlFromStream() throws FileNotFoundException {
        SimpleSong ss = new XStreamParser().load(cache.fetchAsStream(dvlUrl));
        assertEquals(new Float(130.0), ss.bpm);
        assertEquals(TestMp3s.returning.mediaFile.fileName, ss.mediaFile.fileName);
    }

    @Test
    public void loadingFromDiskWithoutDownloading() throws Exception {
        SimpleSong ss = new XStreamParser().load(cache.fetchAsStream(dvlUrl));
        //Retrieve mp3 file
        File fileInRepository = cache.fetchFromInternetOrRepository(ss.mediaFile.fileName, ss.mediaFile.checksum);
        assertTrue(cache.existsInRepository(fileInRepository, ss.mediaFile.checksum));
        assertEquals("/tmp/vdvil/files/cab1562d1198804b5fb6d62a69004488/default", fileInRepository.getAbsolutePath());
    }
}
