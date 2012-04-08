package no.lau.vdvil.mix;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;
import no.bouvet.kpro.renderer.audio.AudioPlaybackTarget;
import no.bouvet.kpro.renderer.audio.AudioRenderer;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.TestMp3s;
import no.vdvil.renderer.audio.Track;
import no.vdvil.renderer.image.ImageRenderer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static no.lau.vdvil.mix.SuperPlayingSetup.*;

public class RendererPlayingTest {
    DownloadAndParseFacade downloadAndParseFacade = PreconfiguredVdvilPlayer.downloadAndParseFacade;
    Composition composition;
    MasterBeatPattern beatPattern = new MasterBeatPattern(0, 18, 120F);

    @Before
    public void before() throws IOException {
        composition = compose(beatPattern);
        composition.cache(downloadAndParseFacade);
    }

    @Test
    public void withRenderer() throws IOException, InterruptedException {
        Composition composition = compose(new MasterBeatPattern(0, 18, 120F));
        composition.cache(downloadAndParseFacade);
        Instructions instructions1 = composition.instructions(120F);
        Renderer renderer = new Renderer(instructions1);
        renderer.addRenderer(new AudioRenderer(new AudioPlaybackTarget()));
        renderer.addRenderer(new ImageRenderer(200, 600, downloadAndParseFacade));
        renderer.start(0);
        while(renderer.isRendering())
            Thread.sleep(1000);
    }

    @Test
    @Ignore
    public void smokingGunwithAudioRenderer() throws IOException {
        AudioRenderer audioRenderer = new AudioRenderer(new AudioPlaybackTarget());
        List<Instruction> instructions = composition.instructions(beatPattern.masterBpm).lock();
        audioRenderer.handleInstruction(0, instructions.get(0));
        audioRenderer.handleInstruction(0, instructions.get(1));
        audioRenderer.handleInstruction(0, instructions.get(2));
        audioRenderer.handleInstruction(0, instructions.get(3));
        audioRenderer.run();
    }




    protected Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
        Track returning = TestMp3s.returning;
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();

        downloadAndParseFacade.fetchAsStream(TestMp3s.returningMp3);
        downloadAndParseFacade.fetchAsStream(TestMp3s.returningDvl);

        parts.add(createAudioPart(returning.segments.get(3).id, new Interval(0, 4), TestMp3s.returningDvl, downloadAndParseFacade));
        parts.add(createAudioPart(returning.segments.get(6).id, new Interval(4, 12), TestMp3s.returningDvl, downloadAndParseFacade));
        parts.add(createAudioPart(returning.segments.get(9).id, new Interval(2, 18), TestMp3s.returningDvl, downloadAndParseFacade));
        parts.add(createAudioPart(returning.segments.get(10).id, new Interval(48, 12), TestMp3s.returningDvl, downloadAndParseFacade));
        parts.add(createImagePart("1", new Interval(0,  16), new URL("http://farm6.static.flickr.com/5101/5659214954_5c333c4cd1_d.jpg")));
        parts.add(createImagePart("2", new Interval(8, 16), new URL("http://farm6.static.flickr.com/5181/5659204108_975723a0fe_d.jpg")));
        parts.add(createImagePart("3", new Interval(32, 16), new URL("http://farm6.static.flickr.com/5187/5620387714_f2bb05064b_d.jpg")));
        parts.add(createImagePart("4", new Interval(48, 16), new URL("http://farm6.static.flickr.com/5308/5620385926_1fe89c1011_d.jpg")));
        parts.add(createImagePart("5", new Interval(64, 16), new URL("http://farm6.static.flickr.com/5068/5620372140_6fdf929526_d.jpg")));
        return new Composition(getClass().getSimpleName(), masterBeatPattern, parts, TestMp3s.NULL);
    }
}
