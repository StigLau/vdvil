package no.lau.vdvil.mix;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.mix.util.CompositionHelper;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.Interval;
import no.vdvil.renderer.audio.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.Track;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class PlayingAMixTest {
    FileRepresentation psylteDvl = TestMp3s.psylteDvl;
    Track returning = TestMp3s.returning;
    MasterBeatPattern mbp = new MasterBeatPattern(0, 16, 130F);

    @Test
    public void play() throws IOException {
        new PreconfiguredVdvilPlayer().init(composition).playUntilEnd();
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