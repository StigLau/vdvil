package no.lau.vdvil.mix;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.mix.util.CompositionHelper;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.TestMp3s;
import no.vdvil.renderer.audio.Track;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class ReturningMixTest {
    Track returning = TestMp3s.returning;

    @Test
    public void play() {
        new PreconfiguredVdvilPlayer().init(composition).playUntilEnd();
    }

    Composition composition = new CompositionHelper() {
        public Composition compose() {
            List<MultimediaPart> parts = new ArrayList<>();
            parts.add(createAudioPart(returning.segments.get(3).id, new Interval(0, 16), TestMp3s.returningDvl));
            parts.add(createAudioPart(returning.segments.get(6).id, new Interval(12, 20), TestMp3s.returningDvl));
            parts.add(createAudioPart(returning.segments.get(9).id, new Interval(32, 30), TestMp3s.returningDvl));
            parts.add(createAudioPart(returning.segments.get(10).id, new Interval(62, 1), TestMp3s.returningDvl));
            parts.add(createAudioPart(returning.segments.get(11).id, new Interval(63, 1), TestMp3s.returningDvl));
            parts.add(createAudioPart(returning.segments.get(12).id, new Interval(64, 64), TestMp3s.returningDvl));
            parts.add(createAudioPart(returning.segments.get(14).id, new Interval(128, 128), TestMp3s.returningDvl));
            return new Composition(getClass().getSimpleName(), new MasterBeatPattern(8, 20, 130F), parts, FileRepresentation.NULL);
        }
    }.compose();
}
