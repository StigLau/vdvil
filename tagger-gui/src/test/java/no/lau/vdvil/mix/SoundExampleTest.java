package no.lau.vdvil.mix;

import no.vdvil.renderer.audio.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoundExampleTest extends SuperPlayingSetup {
    @Test
    public void play() {
        super.play(new MasterBeatPattern(0, 8, 135F));
    }

    @Override
    protected Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(JavaZoneExample.createAudioPart("2754708889643705332", 0, 16, TestMp3s.returningDvl, downloader));
        parts.add(JavaZoneExample.createAudioPart("30189981949854134", 12, 32, TestMp3s.returningDvl, downloader));
        parts.add(JavaZoneExample.createAudioPart("3657904262668647219", 32, 64, TestMp3s.returningDvl, downloader));
        parts.add(JavaZoneExample.createAudioPart("3378726703924324403", 62, 63, TestMp3s.returningDvl, downloader));
        parts.add(JavaZoneExample.createAudioPart("4823965795648964701", 63, 64, TestMp3s.returningDvl, downloader));
        parts.add(JavaZoneExample.createAudioPart("5560598317419002938", 64, 128, TestMp3s.returningDvl, downloader));
        parts.add(JavaZoneExample.createAudioPart("5762690949488488062", 128, 256, TestMp3s.returningDvl, downloader));
        return new no.lau.vdvil.handler.Composition("SoundExampleTest", masterBeatPattern, parts, TestMp3s.NULL);
    }
}