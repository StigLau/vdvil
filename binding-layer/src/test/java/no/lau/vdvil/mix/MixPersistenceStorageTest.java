package no.lau.vdvil.mix;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.mix.util.CompositionHelper;
import no.lau.vdvil.playback.PreconfiguredWavSerializer;
import no.lau.vdvil.timing.Interval;
import no.vdvil.renderer.audio.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.Track;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MixPersistenceStorageTest {
    FileRepresentation psylteDvl = TestMp3s.psylteDvl;
    Track returning = TestMp3s.returning;
    MasterBeatPattern mbp = new MasterBeatPattern(0, 16, 130F);

    @Test
    public void play() throws IOException {
        File testFile = new File("/tmp/" + getClass().getSimpleName() + new Random().nextInt() + ".wav");
        new PreconfiguredWavSerializer(testFile).init(composition).playUntilEnd();
        String fileCheckSum = DigestUtils.md5Hex(new FileInputStream(testFile));
        assertEquals("1758f12b33f68880dc0953aa04292c85", fileCheckSum);
        if(!testFile.delete()) {
            fail("Testfile " + testFile + " could not be deleted!");
        }
    }

    Composition composition = new Composition(getClass().getSimpleName(), mbp, FileRepresentation.NULL, new CompositionHelper() {
        public List<MultimediaPart> parts() {
            return Arrays.asList(
                    createAudioPart(returning.segments.get(3).id, new Interval(0, 20), TestMp3s.returningDvl),
                    //createAudioPart("", new Interval(8, 8), psylteDvl, downloader)); //psylteFlesk.segments.get(4)
                    createAudioPart(returning.segments.get(6).id, new Interval(12, 40), TestMp3s.returningDvl),
                    createAudioPart(returning.segments.get(3).id, new Interval(20, 12), TestMp3s.returningDvl));
        }
    });
}