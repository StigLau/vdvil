package no.lau.vdvil.mix;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.mix.util.CompositionHelper;
import no.lau.vdvil.playback.BackStage;
import no.lau.vdvil.timing.Interval;
import no.vdvil.renderer.audio.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.util.Arrays;
import java.util.List;

/**
 * Simple test for setting up a test of lyric/GUI and music
 */
public class AudioAndLyricsTest {
    final FileRepresentation returningDvl = TestMp3s.returningDvl;
    final MasterBeatPattern mbp = new MasterBeatPattern(0, 20, 150F);

    @Test
    @Tag("IntegrationTest")
    public void play() {
        new BackStage().prepare(composition).playUntilEnd();
    }

    final Composition composition = new Composition(getClass().getSimpleName(), mbp, FileRepresentation.NULL, new CompositionHelper() {
        public List<MultimediaPart> parts() {
            return Arrays.asList(
                    createAudioPart("2754708889643705332", new Interval(0, 16), returningDvl),
                    createLyricPart("Hello World<!", new Interval(8, 4)),
                    createAudioPart("30189981949854134", new Interval(12, 20), returningDvl),
                    createLyricPart("Stig er kul!", new Interval(12, 4)),
                    createAudioPart("3657904262668647219", new Interval(32, 30), returningDvl),
                    createLyricPart("And so on!", new Interval(16, 2)),
                    createAudioPart("3378726703924324403", new Interval(62, 1), returningDvl),
                    createAudioPart("4823965795648964701", new Interval(63, 1), returningDvl),
                    createAudioPart("5560598317419002938", new Interval(64, 64), returningDvl),
                    createAudioPart("5762690949488488062", new Interval(64 * 2, 64 * 2), returningDvl));
        }
    });
}
