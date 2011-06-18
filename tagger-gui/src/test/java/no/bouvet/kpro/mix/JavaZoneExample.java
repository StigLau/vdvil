package no.bouvet.kpro.mix;

import no.lau.vdvil.cache.testresources.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.DvlXML;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.lyric.LyricDescription;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JavaZoneExample {
    URL returning = TestMp3s.returningDvl;
    URL not_alone = TestMp3s.not_aloneDvl;
    URL scares_me = TestMp3s.scares_meDvl;

    PreconfiguredVdvilPlayer vdvilPlayer;

    JavaZoneExample() throws IllegalAccessException, IOException, InterruptedException {
        vdvilPlayer = new PreconfiguredVdvilPlayer();
        vdvilPlayer.init(composition(new MasterBeatPattern(0, 252, 150F)));
        vdvilPlayer.play(0);
        while (vdvilPlayer.isPlaying())
            Thread.sleep(500);
    }

    public static void main(String[] args) throws Exception {
        new JavaZoneExample();
    }

    public Composition composition(MasterBeatPattern masterBeatPattern) throws IOException {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(createAudioPart("4479230163500364845", 0, 32, not_alone, vdvilPlayer.accessCache()));
        parts.add(createAudioPart("5403996530329584526", 16, 48, scares_me, vdvilPlayer.accessCache()));
        parts.add(createAudioPart("8313187524105777940", 32, 70, not_alone, vdvilPlayer.accessCache()));
        parts.add(createAudioPart("5403996530329584526", 48, 64, scares_me, vdvilPlayer.accessCache()));
        parts.add(createAudioPart("1826025806904317462", 64, 112, scares_me, vdvilPlayer.accessCache()));
        parts.add(createAudioPart("6401936245564505757", 96, 140, returning, vdvilPlayer.accessCache()));
        parts.add(createAudioPart("6401936245564505757", 96, 140, returning, vdvilPlayer.accessCache()));
        parts.add(createAudioPart("6182122145512625145", 128, 174, returning, vdvilPlayer.accessCache()));
        parts.add(createAudioPart("3378726703924324403", 144, 174, returning, vdvilPlayer.accessCache()));
        parts.add(createAudioPart("4823965795648964701", 174, 175, returning, vdvilPlayer.accessCache()));
        parts.add(createAudioPart("5560598317419002938", 175, 176, returning, vdvilPlayer.accessCache()));
        parts.add(createAudioPart("9040781467677187716", 176, 240, returning, vdvilPlayer.accessCache()));
        parts.add(createAudioPart("8301899110835906945", 208, 224, scares_me, vdvilPlayer.accessCache()));
        parts.add(createAudioPart("5555459205073513470", 224, 252, scares_me, vdvilPlayer.accessCache()));
        return new Composition("JavaZone Demo", masterBeatPattern, parts, TestMp3s.javaZoneComposition);
    }

    public static MultimediaPart createAudioPart(String id, int start, int end, URL url, DownloadAndParseFacade cache) throws IOException {
        return cache.parse(PartXML.create(id, start, end, DvlXML.create("URL Name", url)));
    }
    public static MultimediaPart createLyricPart(String text, int start, int end) throws MalformedURLException {
        return new LyricDescription(text, PartXML.create(text, start, end, DvlXML.create("name", new URL("http://url.com"))));
    }
}
