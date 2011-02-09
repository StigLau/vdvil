package no.bouvet.kpro.tagger;

import no.lau.tagger.model.SimpleSong;
import no.lau.tagger.model.MediaFile;
import no.bouvet.kpro.tagger.persistence.XStreamParser;
import static org.codehaus.httpcache4j.cache.VdvilCacheStuff.*;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void testPlaying() throws Exception {
        String psylteDvl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/loaderror-psylteflesk.dvl";
        SimpleSong s = new XStreamParser().load(fetchAsStream(psylteDvl));
        fetchAsStream(s.mediaFile.fileName);//Caching
        assertTrue(existsInRepository(fileLocation(s.mediaFile.fileName), s.mediaFile.checksum));//Verifying
        fileLocation(s.mediaFile.fileName);//Ready for using
        SimpleSong simpleSong2 = new SimpleSong(s.reference, new MediaFile(s.mediaFile.fileName,"", s.mediaFile.startingOffset), s.segments, s.bpm);


        PlayerBase playerBase = new PlayerBase(simpleSong2);
        playerBase.playPause(96, 100);
        Thread.sleep(4000);
    }
}
