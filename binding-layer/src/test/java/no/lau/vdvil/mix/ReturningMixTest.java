package no.lau.vdvil.mix;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.mix.util.CompositionHelper;
import no.lau.vdvil.playback.BackStage;
import no.lau.vdvil.playback.VdvilWavConfig;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.TestMp3s;
import no.vdvil.renderer.audio.Track;
import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class ReturningMixTest {
    static Track returning = TestMp3s.returning;
    static MasterBeatPattern mbp = new MasterBeatPattern(0, 256, 130F);

    @Test
    public void persistenceTest() throws IOException {
        VdvilWavConfig asFile = new VdvilWavConfig(this);
        new BackStage(asFile).prepare(composition).playUntilEnd();
        assertEquals("d1134d514e7bb5a7d95123eac64f1d1e", asFile.checksum());
    }
    public static void main(String[] args) throws Exception {
        new BackStage().prepare(composition).playUntilEnd();
    }

    static Composition composition = new Composition(ReturningMixTest.class.getSimpleName(), mbp, FileRepresentation.NULL, new CompositionHelper() {
        public List<MultimediaPart> parts() {
            return Arrays.asList(
                    createAudioPart(returning.segments.get(3).id, new Interval(0, 16), TestMp3s.returningDvl),
                    createAudioPart(returning.segments.get(6).id, new Interval(12, 20), TestMp3s.returningDvl),
                    createAudioPart(returning.segments.get(9).id, new Interval(32, 30), TestMp3s.returningDvl),
                    createAudioPart(returning.segments.get(10).id, new Interval(62, 1), TestMp3s.returningDvl),
                    createAudioPart(returning.segments.get(11).id, new Interval(63, 1), TestMp3s.returningDvl),
                    createAudioPart(returning.segments.get(12).id, new Interval(64, 64), TestMp3s.returningDvl),
                    createAudioPart(returning.segments.get(14).id, new Interval(128, 128), TestMp3s.returningDvl));
        }
    });
}
