package no.bouvet.kpro;

import no.bouvet.kpro.mix.JavaZoneExample;
import no.vdvil.renderer.audio.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SoundExampleTest {
    URL returningDvl = TestMp3s.returningDvl;
    PreconfiguredVdvilPlayer vdvilPlayer;

    @Test
    public void playTest() throws Exception {
        vdvilPlayer = new PreconfiguredVdvilPlayer();
        vdvilPlayer.init(composition(new MasterBeatPattern(0, 8, 135F)));
        vdvilPlayer.play(0);
        while(vdvilPlayer.isPlaying())
            Thread.sleep(200);
    }

    public Composition composition(MasterBeatPattern masterBeatPattern) throws IOException {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(JavaZoneExample.createAudioPart("2754708889643705332", 0, 16, returningDvl, vdvilPlayer.accessCache()));
        parts.add(JavaZoneExample.createAudioPart("30189981949854134", 12, 32, returningDvl, vdvilPlayer.accessCache()));
        parts.add(JavaZoneExample.createAudioPart("3657904262668647219", 32, 64, returningDvl, vdvilPlayer.accessCache()));
        parts.add(JavaZoneExample.createAudioPart("3378726703924324403", 62, 63, returningDvl, vdvilPlayer.accessCache()));
        parts.add(JavaZoneExample.createAudioPart("4823965795648964701", 63, 64, returningDvl, vdvilPlayer.accessCache()));
        parts.add(JavaZoneExample.createAudioPart("5560598317419002938", 64, 128, returningDvl, vdvilPlayer.accessCache()));
        parts.add(JavaZoneExample.createAudioPart("5762690949488488062", 128, 256, returningDvl, vdvilPlayer.accessCache()));
        return new no.lau.vdvil.handler.Composition("SoundExampleTest", masterBeatPattern, parts, TestMp3s.NULL);
    }
}