package no.bouvet.kpro.tagger;

import no.lau.tagger.model.SimpleSong;
import no.lau.tagger.model.MediaFile;
import no.bouvet.kpro.tagger.persistence.XStreamParser;

import static org.junit.Assert.assertTrue;
import no.lau.vdvil.cache.VdvilCache;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import org.junit.Test;

public class PlayerTest {
    VdvilCache cache = VdvilHttpCache.create();

    @Test
    public void testPlaying() throws Exception {
        String psylteDvl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/loaderror-psylteflesk.dvl";
        SimpleSong s = new XStreamParser().load(cache.fetchAsStream(psylteDvl));
        cache.fetchAsStream(s.mediaFile.fileName);//Caching
        assertTrue(cache.existsInRepository(cache.fileLocation(s.mediaFile.fileName), s.mediaFile.checksum));//Verifying
        cache.fileLocation(s.mediaFile.fileName);//Ready for using
        SimpleSong simpleSong2 = new SimpleSong(s.reference, new MediaFile(s.mediaFile.fileName,"", s.mediaFile.startingOffset), s.segments, s.bpm);


        PlayerBase playerBase = new PlayerBase(simpleSong2);
        playerBase.playPause(96, 100);
        Thread.sleep(4000);
    }
}
