package no.lau.vdvil;

import no.lau.vdvil.cache.testresources.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.junit.Test;
import java.io.IOException;

/**
 * Test out downloading and playing with the new (2011) handler interface
 */
public class CompositionHandlerDownloadingAndPlayingTest {
    @Test
    public void testSimpleJavaZonePlayer() throws IllegalAccessException, IOException, InterruptedException {
        PreconfiguredVdvilPlayer vdvilPlayer = new PreconfiguredVdvilPlayer();
        Composition composition = (Composition) vdvilPlayer.accessCache().parse(PartXML.create(TestMp3s.javaZoneComposition));

        vdvilPlayer.init(composition, new MasterBeatPattern(0, 32, 150F));
        vdvilPlayer.play(0);
        while(vdvilPlayer.isPlaying()) {
            Thread.sleep(500);
        }
    }
}
