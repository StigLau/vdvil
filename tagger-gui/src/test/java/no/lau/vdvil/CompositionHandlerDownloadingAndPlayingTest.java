package no.lau.vdvil;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.audio.AudioPlaybackTarget;
import no.bouvet.kpro.renderer.audio.AudioRenderer;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Test out downloading and playing with the new (2011) handler interface
 */
public class CompositionHandlerDownloadingAndPlayingTest {
    @Test
    public void playThis() throws IOException, LineUnavailableException, InterruptedException {
        URL compositionURL = new URL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml");
        DownloadAndParseFacade downloadAndParseFacade = new DownloadAndParseFacade();
        downloadAndParseFacade.addCache(VdvilHttpCache.create());
        downloadAndParseFacade.addParser(new CompositionXMLParser(downloadAndParseFacade));
        downloadAndParseFacade.addParser(new ImageDescriptionXMLParser(downloadAndParseFacade));
        downloadAndParseFacade.addParser(new AudioXMLParser(downloadAndParseFacade));

        Composition composition = (Composition) downloadAndParseFacade.parse("", compositionURL);
        Instructions instructions = composition.instructions(0, 128, 120F);

        System.out.println("instructions = " + instructions);
        List<AbstractRenderer> renderers = new ArrayList<AbstractRenderer>();
        renderers.add(new ImageRenderer(100, 100, downloadAndParseFacade));
        renderers.add(new AudioRenderer(new AudioPlaybackTarget()));

        VdvilPlayer player = new InstructionPlayer(instructions, renderers);
        player.play(0);

        Thread.sleep(5000);

            /*
        PlayStuff playStuff = new PlayStuff(new Composition(130F, parts));
        playStuff.play(4F);
        Thread.sleep(4*1000*130/60);
        playStuff.stop();
        Thread.sleep(50);
        */

    }
}
