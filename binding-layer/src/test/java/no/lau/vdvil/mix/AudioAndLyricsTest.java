package no.lau.vdvil.mix;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.mix.util.CompositionHelper;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.Interval;
import no.vdvil.renderer.audio.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple test for setting up a test of lyric/GUI and music
 */
public class AudioAndLyricsTest {
    @Test
    public void play() {
        new PreconfiguredVdvilPlayer().init(new CompositionHelper() {
            public Composition compose() {
                List<MultimediaPart> parts = new ArrayList<>();
                parts.add(createAudioPart("2754708889643705332", new Interval(0, 16), returningDvl));
                parts.add(createLyricPart("Hello World<!", new Interval(8, 4)));
                parts.add(createAudioPart("30189981949854134", new Interval(12, 20), returningDvl));
                parts.add(createLyricPart("Stig er kul!", new Interval(12, 4)));
                parts.add(createAudioPart("3657904262668647219", new Interval(32, 30), returningDvl));
                parts.add(createLyricPart("And so on!", new Interval(16, 2)));
                parts.add(createAudioPart("3378726703924324403", new Interval(62, 1), returningDvl));
                parts.add(createAudioPart("4823965795648964701", new Interval(63, 1), returningDvl));
                parts.add(createAudioPart("5560598317419002938", new Interval(64, 64), returningDvl));
                parts.add(createAudioPart("5762690949488488062", new Interval(64*2, 64*2), returningDvl));
                return new Composition(getClass().getSimpleName(), new MasterBeatPattern(0, 20, 150F), parts, FileRepresentation.NULL);
            }
        }.compose()).playUntilEnd();
    }

    FileRepresentation returningDvl = TestMp3s.returningDvl;
}
