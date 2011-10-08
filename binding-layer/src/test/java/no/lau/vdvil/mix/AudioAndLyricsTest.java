package no.lau.vdvil.mix;

import no.vdvil.renderer.audio.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple test for setting up a test of lyric/GUI and music
 */
public class AudioAndLyricsTest extends SuperPlayingSetup {
    @Test
    public void play() {
        super.play(new MasterBeatPattern(0, 20, 135F));
    }

    URL returningDvl = TestMp3s.returningDvl;

    @Override
    protected Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(createAudioPart("2754708889643705332", 0, 16, returningDvl, downloader));
        parts.add(createLyricPart("Hello World<!", 8, 12));
        parts.add(createAudioPart("30189981949854134", 12, 32, returningDvl, downloader));
        parts.add(createLyricPart("Stig er kul!", 12, 16));
        parts.add(createAudioPart("3657904262668647219", 32, 62, returningDvl, downloader));
        parts.add(createLyricPart("And so on!", 16, 18));
        parts.add(createAudioPart("3378726703924324403", 62, 63, returningDvl, downloader));
        parts.add(createAudioPart("4823965795648964701", 63, 64, returningDvl, downloader));
        parts.add(createAudioPart("5560598317419002938", 64, 128, returningDvl, downloader));
        parts.add(createAudioPart("5762690949488488062", 128, 256, returningDvl, downloader));
        return new Composition(getClass().getSimpleName(), masterBeatPattern, parts, TestMp3s.NULL);
    }
}
