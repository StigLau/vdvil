package no.bouvet.kpro.tagger;

import no.lau.tagger.model.SimpleSong;
import no.lau.tagger.model.MediaFile;
import no.bouvet.kpro.tagger.persistence.XStreamParser;

import no.lau.vdvil.cache.VdvilCache;
import no.lau.vdvil.cache.testresources.TestMp3s;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import org.junit.Test;

public class PlayerTest {
    VdvilCache cache = VdvilHttpCache.create();

    @Test
    public void testPlaying() throws Exception {

        SimpleSong s = new XStreamParser().load(cache.fetchAsStream(TestMp3s.psylteDvl));
        SimpleSong simpleSong2 = new SimpleSong(s.reference, new MediaFile(s.mediaFile.fileName, s.mediaFile.checksum, s.mediaFile.startingOffset), s.segments, s.bpm);

        PlayerBase playerBase = new PlayerBase(simpleSong2);
        playerBase.playPause(96, 100);
        Thread.sleep(4000);
    }
}
