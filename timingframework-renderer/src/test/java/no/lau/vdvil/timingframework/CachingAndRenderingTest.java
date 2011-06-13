package no.lau.vdvil.timingframework;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Renderer;
import no.bouvet.kpro.renderer.audio.AudioPlaybackTarget;
import no.bouvet.kpro.renderer.audio.AudioRenderer;
import no.lau.vdvil.cache.testresources.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.player.VdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.lau.vdvil.timingframework.renderertarget.TimingInstruction;
import no.lau.vdvil.timingframework.renderertarget.VdvilRenderingTimingTarget;
import no.vdvil.renderer.audio.AudioXMLParser;
import no.vdvil.renderer.image.ImageRenderer;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescription;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;
import org.junit.Before;
import org.junit.Test;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * A full test with the OldSchool way of handling stuff
 */
public class CachingAndRenderingTest {

    URL compositionURL = TestMp3s.javaZoneComposition;
    DownloadAndParseFacade downloadAndParseFacade = new DownloadAndParseFacade();
    Composition composition;
    private ImageRenderer imageRenderer = new ImageRenderer(800, 600, downloadAndParseFacade);
    private AudioRenderer audioRenderer;
    private Renderer oldAudioRenderer;

    @Before
    public void setUp() throws IOException, LineUnavailableException {
        downloadAndParseFacade.addCache(VdvilHttpCache.create());
        downloadAndParseFacade.addParser(new CompositionXMLParser(downloadAndParseFacade));
        downloadAndParseFacade.addParser(new ImageDescriptionXMLParser(downloadAndParseFacade));
        downloadAndParseFacade.addParser(new AudioXMLParser(downloadAndParseFacade));
        composition = (Composition) downloadAndParseFacade.parse(PartXML.create(compositionURL));
        audioRenderer = new AudioRenderer(new AudioPlaybackTarget());
        //imageRenderer.start(0);
    }

    @Test
    public void cacheAndRun() throws IOException, InterruptedException {

        MasterBeatPattern beatPattern = new MasterBeatPattern(0, 68, 150F);
        composition.masterBpm = 150F;
        TimingInstruction[] timingInstructions = createInstructionsFromSomethingElse(composition, beatPattern);

        //Hardcode a playin
        timingInstructions[0].beatPattern = beatPattern.duration(0, 16);
        timingInstructions[1].beatPattern = beatPattern.duration(32, 64);

        VdvilPlayer timingPlayer = new TimingFrameworkMasterRenderer(beatPattern,
                new VdvilRenderingTimingTarget(timingInstructions, beatPattern), timingInstructions[0].renderingTarget);

        //For testing audio Rendering
        oldAudioRenderer = new Renderer(composition.instructions(beatPattern.masterBpm));
        oldAudioRenderer.addRenderer(audioRenderer);

        oldAudioRenderer.start(0);

        timingPlayer.play(0);
        while(timingPlayer.isPlaying()) {
            Thread.sleep(1000);
        }
    }

    private TimingInstruction[] createInstructionsFromSomethingElse(Composition composition, MasterBeatPattern mbp) {
        ArrayList<TimingInstruction> timingInstructions = new ArrayList<TimingInstruction>();
        for (MultimediaPart multimediaPart : composition.multimediaParts) {
            AbstractRenderer renderer = null;
            //if (multimediaPart instanceof AudioDescription)
                //renderer = audioRenderer;
            if(multimediaPart instanceof ImageDescription) {
                renderer = imageRenderer;
                timingInstructions.add(new TimingInstruction(
                        mbp.duration(multimediaPart.compositionInstruction().start(), multimediaPart.compositionInstruction().end()),
                        new VdvilTimingTarget(multimediaPart, renderer)));
            }
        }
        return timingInstructions.toArray(new TimingInstruction[] {});
    }
}

class VdvilTimingTarget extends TimingTargetAdapter {

    private MultimediaPart multimediaPart;
    private AbstractRenderer renderer;

    public VdvilTimingTarget(MultimediaPart multimediaPart, AbstractRenderer renderer) {
        this.multimediaPart = multimediaPart;
        this.renderer = renderer;
    }

    public void begin(Animator source) {
        renderer.handleInstruction(0, multimediaPart.asInstruction(0F));
        renderer.start(0);
        System.out.println("Vdvil " + multimediaPart + " timing Target Begin");
    }

    public void end(Animator source) {
        renderer.stop();
        System.out.println("Vdvil Timing Target End");
    }
}
