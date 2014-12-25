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
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayingAMixTest {
    FileRepresentation psylteDvl = TestMp3s.psylteDvl;
    Track returning = TestMp3s.returning;

    @Test
    public void play() throws IOException {
        new PreconfiguredVdvilPlayer().init(new CompositionHelper() {
            public Composition compose() {
                List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
                parts.add(createAudioPart(returning.segments.get(3).id, new Interval(0, 20), TestMp3s.returningDvl));
                //parts.add(createAudioPart("", new Interval(8, 8), psylteDvl, downloader)); //psylteFlesk.segments.get(4)
                parts.add(createAudioPart(returning.segments.get(6).id, new Interval(12, 40), TestMp3s.returningDvl));
                parts.add(createAudioPart(returning.segments.get(3).id, new Interval(20, 12), TestMp3s.returningDvl));
                return new Composition(getClass().getSimpleName(), new MasterBeatPattern(0, 16, 130F), parts, FileRepresentation.NULL);
            }
        }).playUntilEnd();
    }
}