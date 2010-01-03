package no.bouvet.kpro.tagger;

import org.testng.annotations.Test;
import no.bouvet.kpro.tagger.model.SimpleSong;
import no.bouvet.kpro.tagger.persistence.XStreamParser;

public class PlayerTest {

    @Test
    public void testPlaying() throws Exception {
        System.out.println("PlayerTest playing Holden and Thompson");
        SimpleSong simpleSong = new XStreamParser().load(XStreamParser.path + "/holden-nothing-93_returning_mix.dvl");
        PlayerBase playerBase = new PlayerBase(simpleSong);
        playerBase.playPause(96F, 100F);
        Thread.sleep(4000);
    }
}
