package no.bouvet.kpro;

import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.lau.vdvil.cache.testresources.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.DvlXML;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple test for setting up a test of lyric/GUI and music
 */
public class AudioAndLyricsExample {
    Logger log = LoggerFactory.getLogger(getClass());
    private static URL returningDvl = TestMp3s.returningDvl;
    PreconfiguredVdvilPlayer vdvilPlayer;

    public static void main(String[] args) throws Exception {
        new AudioAndLyricsExample().setUpStuff();
    }

    private void setUpStuff() throws Exception {
        vdvilPlayer = new PreconfiguredVdvilPlayer();

        vdvilPlayer.init(parts(), new MasterBeatPattern(0, 8, 135F));
        vdvilPlayer.play(0);
        while(vdvilPlayer.isPlaying()) {
            Thread.sleep(500);
        }
        //new LyricRenderer(1000, 100)
        //TODO Not working yet!!!
    }

    public Composition parts() throws FileNotFoundException {

        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        try {
            parts.add(createAudioPart("2754708889643705332", 0, 16, returningDvl));
            parts.add(createLyricPart("Hello World!", 0, 12));
            parts.add(createAudioPart("30189981949854134", 12, 32, returningDvl));
            parts.add(createLyricPart("Stig er kul!", 12, 32));
            parts.add(createAudioPart("3657904262668647219", 32, 62, returningDvl));
            parts.add(createLyricPart("And so on!", 32, 62));
            parts.add(createAudioPart("3378726703924324403", 62, 63, returningDvl));
            parts.add(createAudioPart("4823965795648964701", 63, 64, returningDvl));
            parts.add(createAudioPart("5560598317419002938", 64, 128, returningDvl));
            parts.add(createAudioPart("5762690949488488062", 128, 256, returningDvl));
            } catch (IOException e) {
            throw new RuntimeException("This should not happen");
        }
        return new no.lau.vdvil.handler.Composition("JavaZone Demo", 150F, parts, TestMp3s.javaZoneComposition);
    }

    private MultimediaPart createLyricPart(String text, int start, int end) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    MultimediaPart createAudioPart(String id, int start, int end, URL url) throws IOException {
        return vdvilPlayer.accessCache().parse(PartXML.create(id, start, end, DvlXML.create("URL Name", url)));
    }
}
