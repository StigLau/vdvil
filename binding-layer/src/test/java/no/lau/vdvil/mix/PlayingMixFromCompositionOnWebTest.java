package no.lau.vdvil.mix;

import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.TestMp3s;
import org.junit.Test;

import java.io.IOException;

public class PlayingMixFromCompositionOnWebTest {

    @Test
    public void play() throws IOException, IllegalAccessException, InterruptedException {
        PreconfiguredVdvilPlayer vdvilPlayer = new PreconfiguredVdvilPlayer();
        Composition composition = (Composition) vdvilPlayer.accessCache().parse(PartXML.create(TestMp3s.javaZoneComposition));
        vdvilPlayer.init(composition, new MasterBeatPattern(0, 16, 150F));
        vdvilPlayer.play(0);
        while(vdvilPlayer.isPlaying())
            Thread.sleep(200);
    }
}
