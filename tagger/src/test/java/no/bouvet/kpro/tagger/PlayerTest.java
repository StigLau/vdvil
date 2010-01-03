package no.bouvet.kpro.tagger;

import org.testng.annotations.Test;
import no.bouvet.kpro.tagger.model.SimpleSong;
import no.bouvet.kpro.tagger.persistence.XStreamParser;

public class PlayerTest {

    @Test
    public void testPlaying() throws Exception {
        XStreamParser parser = new XStreamParser<SimpleSong>();
        //SimpleSong simpleSong = parser.load(parser.path + "/corona.dvl");
        SimpleSong simpleSong = parser.load(parser.path + "/holden-nothing-93_returning_mix.dvl");
        //Worker worker = new Worker(simpleSong, 0F);
        PlayerBase playerBase = new PlayerBase(simpleSong);
        playerBase.playPause(96F, 128F);
        Thread.sleep(50000);
    }
}
