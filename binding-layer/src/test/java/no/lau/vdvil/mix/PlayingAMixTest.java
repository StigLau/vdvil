package no.lau.vdvil.mix;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.mix.util.SuperPlayingSetup;
import no.lau.vdvil.timing.Interval;
import no.vdvil.renderer.audio.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.Track;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlayingAMixTest {
    URL psylteDvl = TestMp3s.psylteDvl;
    Track returning = TestMp3s.returning;

    @Test
    public void play() {
        new SuperPlayingSetup() {
            public Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
                List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
                parts.add(createAudioPart(returning.segments.get(3).id, new Interval(0, 20), TestMp3s.returningDvl));
                //parts.add(createAudioPart("", new Interval(8, 8), psylteDvl, downloader)); //psylteFlesk.segments.get(4)
                parts.add(createAudioPart(returning.segments.get(6).id, new Interval(12, 40), TestMp3s.returningDvl));
                parts.add(createAudioPart(returning.segments.get(3).id, new Interval(20, 12), TestMp3s.returningDvl));
                return new Composition(getClass().getSimpleName(), masterBeatPattern, parts, FileRepresentation.NULL);
            }
        }.play(new MasterBeatPattern(0, 16, 130F));
    }
}