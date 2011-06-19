package no.lau.vdvil;

import static no.bouvet.kpro.mix.JavaZoneExample.createAudioPart;
import no.lau.vdvil.cache.testresources.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlayingAMixTest2 {
    URL returningDvl = TestMp3s.returningDvl;
    URL psylteDvl = TestMp3s.psylteDvl;
    PreconfiguredVdvilPlayer vdvilPlayer;

    @Test
    public void play() throws Exception {
        vdvilPlayer = new PreconfiguredVdvilPlayer();
        vdvilPlayer.init(compose(new MasterBeatPattern(0, 16, 130F)));
        vdvilPlayer.play(0);
        while(vdvilPlayer.isPlaying())
            Thread.sleep(200);
    }

    Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();

        parts.add(createAudioPart("2754708889643705332", 0, 20, returningDvl, vdvilPlayer.accessCache()));
        //parts.add(createAudioPart("", 8, 16, psylteDvl, vdvilPlayer.accessCache())); //psylteFlesk.segments.get(4)
        parts.add(createAudioPart("30189981949854134", 12, 52, returningDvl, vdvilPlayer.accessCache()));
        parts.add(createAudioPart("2754708889643705332", 20, 32, returningDvl, vdvilPlayer.accessCache()));
        return new Composition(getClass().getSimpleName(), masterBeatPattern, parts, TestMp3s.NULL);
    }
}