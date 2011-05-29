package no.lau.vdvil;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.audio.AudioPlaybackTarget;
import no.bouvet.kpro.renderer.audio.AudioRenderer;
import no.lau.vdvil.cache.testresources.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.player.InstructionPlayer;
import no.lau.vdvil.player.VdvilPlayer;
import no.vdvil.renderer.audio.AudioXMLParser;
import no.vdvil.renderer.image.ImageRenderer;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import org.junit.Test;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.URL;

/**
 * Test out downloading and playing with the new (2011) handler interface
 */
public class CompositionHandlerDownloadingAndPlayingTest {
    @Test
    public void playThis() throws IOException, LineUnavailableException, InterruptedException {
        DownloadAndParseFacade downloadAndParseFacade = new DownloadAndParseFacade();
        downloadAndParseFacade.addCache(VdvilHttpCache.create());
        downloadAndParseFacade.addParser(new CompositionXMLParser(downloadAndParseFacade));
        downloadAndParseFacade.addParser(new ImageDescriptionXMLParser(downloadAndParseFacade));
        downloadAndParseFacade.addParser(new AudioXMLParser(downloadAndParseFacade));

        URL compositionURL = TestMp3s.javaZoneComposition;
        Composition composition = (Composition) downloadAndParseFacade.parse("", compositionURL);
        Instructions instructions = composition.instructions(0, 128, 120F);

        AbstractRenderer[] renderers = new AbstractRenderer[] {
                new ImageRenderer(100, 100, downloadAndParseFacade),
                new AudioRenderer(new AudioPlaybackTarget())
        };
        VdvilPlayer player = new InstructionPlayer(instructions, renderers);
        player.play(0);

        Thread.sleep(4*1000*130/60);
    }
}
