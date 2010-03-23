package no.bouvet.kpro.tagger;

import no.lau.tagger.model.SimpleSong;
import no.lau.tagger.model.MediaFile;
import no.bouvet.kpro.tagger.persistence.XStreamParser;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void testPlaying() throws Exception {
        String dvlFile = this.getClass().getClassLoader().getResource ("loaderror-psylteflesk.dvl").getFile();
        String mp3File = this.getClass().getClassLoader().getResource ("loaderror-psylteflesk.mp3").getFile();

        SimpleSong s = new XStreamParser().load(dvlFile);
        SimpleSong simpleSong2 = new SimpleSong(s.reference, new MediaFile(mp3File, s.mediaFile.startingOffset), s.segments, s.bpm);

        PlayerBase playerBase = new PlayerBase(simpleSong2);
        playerBase.playPause(96F, 100F);
        Thread.sleep(4000);
    }
}
