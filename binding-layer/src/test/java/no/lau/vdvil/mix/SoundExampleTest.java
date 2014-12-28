package no.lau.vdvil.mix;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.mix.util.CompositionHelper;
import no.lau.vdvil.playback.BackStage;
import no.lau.vdvil.playback.VdvilWavConfig;
import no.lau.vdvil.timing.Interval;
import no.vdvil.renderer.audio.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class SoundExampleTest {
    static FileRepresentation returning = TestMp3s.returningDvl;
    static MasterBeatPattern mbp = new MasterBeatPattern(0, 256, 135F);

    @Test
    public void persistenceTest() throws IOException {
        VdvilWavConfig asFile = new VdvilWavConfig(this);
        new BackStage(asFile).prepare(composition).playUntilEnd();
        assertEquals("6f6baa326fce891df727f55217b6edca", asFile.checksum());
    }

    public static void main(String[] args) throws Exception {
        new BackStage().prepare(composition).playUntilEnd();
    }

    static Composition composition = new Composition(SoundExampleTest.class.getSimpleName(), mbp, FileRepresentation.NULL, new CompositionHelper() {
        public List<MultimediaPart> parts() {
            return Arrays.asList(
                    createAudioPart("2754708889643705332", new Interval(0, 16), returning),
                    createAudioPart("30189981949854134", new Interval(12, 20), returning),
                    createAudioPart("3657904262668647219", new Interval(32, 32), returning),
                    createAudioPart("3378726703924324403", new Interval(62, 1), returning),
                    createAudioPart("4823965795648964701", new Interval(63, 1), returning),
                    createAudioPart("5560598317419002938", new Interval(64, 64), returning),
                    createAudioPart("5762690949488488062", new Interval(128, 128), returning));
        }
    });
}