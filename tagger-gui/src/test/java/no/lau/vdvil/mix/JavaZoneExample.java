package no.lau.vdvil.mix;

import no.vdvil.renderer.audio.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.timing.MasterBeatPattern;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JavaZoneExample extends SuperPlayingSetup {
    URL returning = TestMp3s.returningDvl;
    URL not_alone = TestMp3s.not_aloneDvl;
    URL scares_me = TestMp3s.scares_meDvl;

    public static void main(String[] args) throws Exception {
        new JavaZoneExample().play(new MasterBeatPattern(0, 252, 150F));
    }

    @Override
    protected Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(createAudioPart("4479230163500364845", 0, 32, not_alone, downloader));
        parts.add(createAudioPart("5403996530329584526", 16, 48, scares_me, downloader));
        parts.add(createAudioPart("8313187524105777940", 32, 70, not_alone, downloader));
        parts.add(createAudioPart("5403996530329584526", 48, 64, scares_me, downloader));
        parts.add(createAudioPart("1826025806904317462", 64, 112, scares_me, downloader));
        parts.add(createAudioPart("6401936245564505757", 96, 140, returning, downloader));
        parts.add(createAudioPart("6401936245564505757", 96, 140, returning, downloader));
        parts.add(createAudioPart("6182122145512625145", 128, 174, returning, downloader));
        parts.add(createAudioPart("3378726703924324403", 144, 174, returning, downloader));
        parts.add(createAudioPart("4823965795648964701", 174, 175, returning, downloader));
        parts.add(createAudioPart("5560598317419002938", 175, 176, returning, downloader));
        parts.add(createAudioPart("9040781467677187716", 176, 240, returning, downloader));
        parts.add(createAudioPart("8301899110835906945", 208, 224, scares_me, downloader));
        parts.add(createAudioPart("5555459205073513470", 224, 252, scares_me, downloader));
        return new Composition(getClass().getSimpleName(), masterBeatPattern, parts, TestMp3s.javaZoneComposition);
    }    
}
