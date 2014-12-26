package no.lau.vdvil.mix;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.TestMp3s;
import org.junit.Test;
import java.io.IOException;

public class PlayingMixFromCompositionOnWebTest {
     ParseFacade parser = new PreconfiguredVdvilPlayer().PARSE_FACADE;
    @Test
    public void play() throws IOException, InterruptedException {
        FileRepresentation fileRepresentation = TestMp3s.javaZoneCompositionJson;
        PreconfiguredVdvilPlayer vdvilPlayer = new PreconfiguredVdvilPlayer();
        Composition composition = (Composition) parser.parse(PartXML.create(fileRepresentation));
        vdvilPlayer.init(composition, new MasterBeatPattern(0, 16, 150F));
        vdvilPlayer.play();
        while(vdvilPlayer.isPlaying())
            Thread.sleep(200);
    }
}
