package no.lau.vdvil.mix;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.mix.util.CompositionHelper;
import no.lau.vdvil.playback.BackStage;
import no.lau.vdvil.playback.VdvilWavConfig;
import no.lau.vdvil.timing.Interval;
import no.vdvil.renderer.audio.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.Track;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MixPersistenceStorageTest {
    FileRepresentation psylteDvl = TestMp3s.psylteDvl;
    final Track returning = TestMp3s.returning;
    final MasterBeatPattern mbp = new MasterBeatPattern(0, 16, 130F);

    @Test
    public void play() throws IOException {
        VdvilWavConfig asFile = new VdvilWavConfig(this);
        new BackStage(asFile).prepare(composition).playUntilEnd();
        assertEquals("1758f12b33f68880dc0953aa04292c85", asFile.checksum());
    }

    final Composition composition = new Composition(getClass().getSimpleName(), mbp, FileRepresentation.NULL, new CompositionHelper() {
        public List<MultimediaPart> parts() {
            return Arrays.asList(
                    createAudioPart(returning.segments.get(3).id, new Interval(0, 20), TestMp3s.returningDvl),
                    //createAudioPart("", new Interval(8, 8), psylteDvl, downloader)); //psylteFlesk.segments.get(4)
                    createAudioPart(returning.segments.get(6).id, new Interval(12, 40), TestMp3s.returningDvl),
                    createAudioPart(returning.segments.get(3).id, new Interval(20, 12), TestMp3s.returningDvl));
        }
    });
}