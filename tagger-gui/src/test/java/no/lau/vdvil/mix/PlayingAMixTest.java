package no.lau.vdvil.mix;

import no.lau.vdvil.handler.MultimediaPart;
import no.vdvil.renderer.audio.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.Track;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlayingAMixTest extends SuperPlayingSetup{
    @Test
    public void play() {
        super.play(new MasterBeatPattern(0, 16, 130F));
    }

    URL psylteDvl = TestMp3s.psylteDvl;
    Track returning = TestMp3s.returning;

    @Override
    protected Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(JavaZoneExample.createAudioPart(returning.segments.get(3).id, 0, 20, TestMp3s.returningDvl, downloader));
        //parts.add(createAudioPart("", 8, 16, psylteDvl, vdvilPlayer.accessCache())); //psylteFlesk.segments.get(4)
        parts.add(JavaZoneExample.createAudioPart(returning.segments.get(6).id, 12, 52, TestMp3s.returningDvl, downloader));
        parts.add(JavaZoneExample.createAudioPart(returning.segments.get(3).id, 20, 32, TestMp3s.returningDvl, downloader));
        return new Composition(getClass().getSimpleName(), masterBeatPattern, parts, TestMp3s.NULL);
    }
}