package no.lau.vdvil.mix;

import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.OldRenderer;
import no.bouvet.kpro.renderer.audio.AudioPlaybackTarget;
import no.bouvet.kpro.renderer.audio.AudioRenderer;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.instruction.Instruction;
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
import java.util.ArrayList;
import java.util.List;
import static no.lau.vdvil.mix.util.SuperPlayingSetup.*;

public class RendererPlayingTest {
    Store store = Store.get();
    Track returningMp3 = TestMp3s.returning;
    Composition composition;
    MasterBeatPattern beatPattern = new MasterBeatPattern(0, 18, 120F);

    @Before
    public void before() throws IOException {
        composition = compose(beatPattern);
    }

    @Test
    public void withRenderer() throws IOException, InterruptedException {
        PreconfiguredVdvilPlayer.cache(composition);
        Instructions instructions1 = composition.instructions(120F, 0);
        OldRenderer renderer = new OldRenderer(instructions1);
        renderer.addRenderer(new AudioRenderer(new AudioPlaybackTarget()));
        renderer.addRenderer(new ImageRenderer(200, 600));
        renderer.start(0);
        while(renderer.isRendering())
            Thread.sleep(1000);
    }

    @Test
    @Ignore
    public void smokingGunwithAudioRenderer() throws IOException {
        AudioRenderer audioRenderer = new AudioRenderer(new AudioPlaybackTarget());
        List<Instruction> instructions = composition.instructions(beatPattern.masterBpm, 0).lock();
        audioRenderer.notify(instructions.get(0), 0);
        audioRenderer.notify(instructions.get(1), 0);
        audioRenderer.notify(instructions.get(2), 0);
        audioRenderer.notify(instructions.get(3), 0);
        audioRenderer.run();
    }

    protected Composition compose(MasterBeatPattern masterBeatPattern) {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(createAudioPart(returningMp3.segments.get(3).id, new Interval(0, 4), TestMp3s.returningDvl));
        parts.add(createAudioPart(returningMp3.segments.get(6).id, new Interval(4, 12), TestMp3s.returningDvl));
        parts.add(createAudioPart(returningMp3.segments.get(9).id, new Interval(2, 18), TestMp3s.returningDvl));
        parts.add(createAudioPart(returningMp3.segments.get(10).id, new Interval(48, 12), TestMp3s.returningDvl));
            parts.add(createImagePart("1", new Interval(0,  16), store.createKey("https://kpro09.googlecode.com/svn/test-files/pics/shiba/killer_shibe_huntin_phor_u_on_snow.jpg", "fbef28324d02fab54085b686cec62947")));
            parts.add(createImagePart("2", new Interval(8, 16), store.createKey("https://kpro09.googlecode.com/svn/test-files/pics/shiba/shibe_puppy_snow.jpg", "20ff41e030878bc25d6f24a9e1d384bf")));
            parts.add(createImagePart("3", new Interval(32, 16), store.createKey("https://kpro09.googlecode.com/svn/test-files/pics/shiba/shibe_lookin_at_u.jpg", "a84415d82a247b110f621ddc4874f3ce")));
            parts.add(createImagePart("4", new Interval(48, 16), store.createKey("https://kpro09.googlecode.com/svn/test-files/pics/shiba/shibe_window.jpg", "facde87bfa52415d669c828ac726119a")));
            parts.add(createImagePart("5", new Interval(64, 16), store.createKey("https://kpro09.googlecode.com/svn/test-files/pics/shiba/yawny_shibe.jpg", "80547837f13b03e412ffb607e4b0841d")));
        
        return new Composition(getClass().getSimpleName(), masterBeatPattern, parts, FileRepresentation.NULL);
    }
}
