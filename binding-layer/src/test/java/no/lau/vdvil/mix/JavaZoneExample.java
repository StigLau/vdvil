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
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class JavaZoneExample {
    FileRepresentation returning = TestMp3s.returningJsonDvl;
    FileRepresentation not_alone = TestMp3s.not_aloneDvl;
    FileRepresentation scares_me = TestMp3s.scares_meDvl;
    public MasterBeatPattern mbp = new MasterBeatPattern(0, 32 + 64 * 3 + 28, 150F);

    @Before
    public void setUp() throws IOException {
        BackStage.cache(composition);
    }
    @Test
    public void persistenceTest() throws IOException {
        VdvilWavConfig asFile = new VdvilWavConfig(this);
        new BackStage(asFile).prepare(composition).playUntilEnd();
        assertEquals("asd123", asFile.checksum());
    }

    public static void main(String[] args) throws Exception {
        new BackStage().prepare(new JavaZoneExample().composition).playUntilEnd();
    }

    Composition composition = new Composition(getClass().getSimpleName(), mbp, FileRepresentation.NULL, new CompositionHelper() {
        public List<MultimediaPart> parts() {
            return Arrays.asList(
                    createAudioPart("4479230163500364845", new Interval(0, 32), not_alone),
                    createAudioPart("5403996530329584526", new Interval(16, 32), scares_me),
                    createAudioPart("8313187524105777940", new Interval(32, 38), not_alone),
                    createAudioPart("5403996530329584526", new Interval(48, 16), scares_me),
                    createAudioPart("1826025806904317462", new Interval(64, 48), scares_me),
                    createAudioPart("6401936245564505757", new Interval(32 + 64, 44), returning),
                    createAudioPart("6401936245564505757", new Interval(32 + 64, 44), returning),
                    createAudioPart("6182122145512625145", new Interval(64 * 2, 46), returning),
                    createAudioPart("3378726703924324403", new Interval(16 + 64 * 2, 30), returning),
                    createAudioPart("4823965795648964701", new Interval(14 + 32 + 64 * 2, 1), returning),
                    createAudioPart("5560598317419002938", new Interval(15 + 32 + 64 * 2, 1), returning),
                    createAudioPart("9040781467677187716", new Interval(16 + 32 + 64 * 2, 64), returning),
                    createAudioPart("8301899110835906945", new Interval(16 + 64 * 3, 16), scares_me),
                    createAudioPart("5555459205073513470", new Interval(32 + 64 * 3, 28), scares_me));
        }
    });
}
