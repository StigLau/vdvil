package no.lau.vdvil.mix;

import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.TestMp3s;
import no.vdvil.renderer.audio.Track;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static no.lau.vdvil.mix.JavaZoneExample.createAudioPart;

public class ReturningMixTest extends SuperPlayingSetup {
    Track returning = TestMp3s.returning;
    @Test
    public void play() {
        super.play(new MasterBeatPattern(32, 68, 130F));
    }
    @Override
    protected Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(createAudioPart(returning.segments.get(3).id, 0, 16, TestMp3s.returningDvl, downloader));
        parts.add(createAudioPart(returning.segments.get(6).id, 12, 32, TestMp3s.returningDvl, downloader));
        parts.add(createAudioPart(returning.segments.get(9).id, 32, 62, TestMp3s.returningDvl, downloader));
        parts.add(createAudioPart(returning.segments.get(10).id, 62, 63, TestMp3s.returningDvl, downloader));
        parts.add(createAudioPart(returning.segments.get(11).id, 63, 64, TestMp3s.returningDvl, downloader));
        parts.add(createAudioPart(returning.segments.get(12).id, 64, 128, TestMp3s.returningDvl, downloader));
        parts.add(createAudioPart(returning.segments.get(14).id, 128, 256, TestMp3s.returningDvl, downloader));
        return new Composition(getClass().getSimpleName(), masterBeatPattern, parts, TestMp3s.NULL);
    }
}
