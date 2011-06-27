package no.lau.vdvil.mix;

import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.TestMp3s;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

public class PlayingMixFromCompositionOnWebTest {

    @Test
    public void play() throws IOException, IllegalAccessException, InterruptedException {
        URL url = TestMp3s.javaZoneComposition;
        //URL url = new URL("file://localhost/Users/stiglau/utvikling/kpro09/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml");
        PreconfiguredVdvilPlayer vdvilPlayer = new PreconfiguredVdvilPlayer();
        Composition composition = (Composition) vdvilPlayer.accessCache().parse(PartXML.create(url));
        vdvilPlayer.init(composition, new MasterBeatPattern(0, 16, 150F));
        vdvilPlayer.play();
        while(vdvilPlayer.isPlaying())
            Thread.sleep(200);
    }
}
