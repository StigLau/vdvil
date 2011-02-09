package no.lau.vdvil;

import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.lau.tagger.model.SimpleSong;
import org.codehaus.httpcache4j.cache.VdvilCacheStuff;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import static org.codehaus.httpcache4j.cache.VdvilCacheStuff.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CacheParsingTest {


    final String dvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl";

    @Test
    public void parsingTheXmlFromStream() throws FileNotFoundException {
        SimpleSong ss = new XStreamParser().load(VdvilCacheStuff.fetchAsStream(dvlUrl));
        assertEquals(new Float(130.0), ss.bpm);
        assertEquals("http://kpro09.googlecode.com/svn/test-files/holden-nothing-93_returning_mix.mp3", ss.mediaFile.fileName);
    }

    @Test
    public void loadingFromDiskWithoutDownloading() throws Exception {
        SimpleSong ss = new XStreamParser().load(VdvilCacheStuff.fetchAsStream(dvlUrl));
        //Retrieve mp3 file
        File fileInRepository = fileLocation(ss.mediaFile.fileName);
        assertTrue(existsInRepository(fileInRepository, ss.mediaFile.checksum));
        assertEquals("/tmp/vdvil/files/cab1562d1198804b5fb6d62a69004488/default", fileInRepository.getAbsolutePath());
    }
}
