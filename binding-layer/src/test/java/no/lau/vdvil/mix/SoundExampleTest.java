package no.lau.vdvil.mix;

import no.lau.vdvil.mix.util.SuperPlayingSetup;
import no.lau.vdvil.timing.Interval;
import no.vdvil.renderer.audio.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoundExampleTest {
    @Test
    public void play() {
        new SuperPlayingSetup() {
            @Override
            public Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
                List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
                parts.add(createAudioPart("2754708889643705332", new Interval(0, 16), TestMp3s.returningDvl, downloader));
                parts.add(createAudioPart("30189981949854134", new Interval(12, 20), TestMp3s.returningDvl, downloader));
                parts.add(createAudioPart("3657904262668647219", new Interval(32, 32), TestMp3s.returningDvl, downloader));
                parts.add(createAudioPart("3378726703924324403", new Interval(62, 1), TestMp3s.returningDvl, downloader));
                parts.add(createAudioPart("4823965795648964701", new Interval(63, 1), TestMp3s.returningDvl, downloader));
                parts.add(createAudioPart("5560598317419002938", new Interval(64, 64), TestMp3s.returningDvl, downloader));
                parts.add(createAudioPart("5762690949488488062", new Interval(128, 128), TestMp3s.returningDvl, downloader));
                return new no.lau.vdvil.handler.Composition("SoundExampleTest", masterBeatPattern, parts, TestMp3s.NULL);
            }
        }.play(new MasterBeatPattern(0, 8, 135F));
    }
}