package no.lau.vdvil.timingframework;

import no.lau.vdvil.cache.testresources.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.player.VdvilPlayer;
import no.lau.vdvil.timingframework.renderertarget.TimingInstruction;
import no.lau.vdvil.timingframework.renderertarget.VdvilRenderingTimingTarget;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescription;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * A full test with the OldSchool way of handling stuff
 */
public class CachingAndRenderingTest {

    URL compositionURL = TestMp3s.javaZoneComposition;
    DownloadAndParseFacade downloadAndParseFacade;
    Composition composition;

    @Before
    public void setUp() throws IOException {
        downloadAndParseFacade = new DownloadAndParseFacade();
        downloadAndParseFacade.addCache(VdvilHttpCache.create());
        downloadAndParseFacade.addParser(new CompositionXMLParser(downloadAndParseFacade));
        downloadAndParseFacade.addParser(new ImageDescriptionXMLParser(downloadAndParseFacade));
        composition = (Composition) downloadAndParseFacade.parse(PartXML.create(compositionURL));
    }

    @Test
    public void cacheAndRun() throws IOException, InterruptedException {

        MasterBeatPattern beatPattern = new MasterBeatPattern(0, 16, 120F);

        TimingInstruction[] timingInstructions = createInstructionsFromSomethingElse(composition, beatPattern);

        VdvilPlayer timingPlayer = new TimingFrameworkMasterRenderer(beatPattern,
                new VdvilRenderingTimingTarget(timingInstructions, beatPattern), timingInstructions[0].renderingTarget);

        timingPlayer.play(0);
        while(timingPlayer.isPlaying()) {
            Thread.sleep(1000);
        }
    }

    private TimingInstruction[] createInstructionsFromSomethingElse(Composition composition, MasterBeatPattern mbp) {
        ArrayList<TimingInstruction> timingInstructions = new ArrayList<TimingInstruction>();
        for (MultimediaPart multimediaPart : composition.multimediaParts) {
            if (multimediaPart instanceof ImageDescription) {
                ImageDescription imageDescription = (ImageDescription) multimediaPart;
                timingInstructions.add(new TimingInstruction(
                        mbp.duration(imageDescription.compositionInstruction.start(), imageDescription.compositionInstruction.end()),
                        new ImageTimingTarget(imageDescription)));

            }
        }
        return timingInstructions.toArray(new TimingInstruction[] {});
    }
}

class ImageTimingTarget extends TimingTargetAdapter {

    public ImageTimingTarget(ImageDescription instruction) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public void begin(Animator source) {
            System.out.println("Image timing Target Begin");
        }

        public void end(Animator source) {
            System.out.println("Image timing Target End");
        }
}
