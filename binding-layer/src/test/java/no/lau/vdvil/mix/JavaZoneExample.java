package no.lau.vdvil.mix;

import no.lau.vdvil.timing.Interval;
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
        //TODO Some Weired problem where Player doesn't stop. Try JavaZoneExample with 64 to 64*2
        new JavaZoneExample().play(new MasterBeatPattern(0, 32+64*3+ 28, 150F));
    }

    @Override
    protected Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(createAudioPart("4479230163500364845", new Interval(0, 32), not_alone, downloader));
        parts.add(createAudioPart("5403996530329584526", new Interval(16, 32), scares_me, downloader));
        parts.add(createAudioPart("8313187524105777940", new Interval(32, 38), not_alone, downloader));
        parts.add(createAudioPart("5403996530329584526", new Interval(48, 16), scares_me, downloader));
        parts.add(createAudioPart("1826025806904317462", new Interval(64, 48), scares_me, downloader));
        parts.add(createAudioPart("6401936245564505757", new Interval(32+64, 44), returning, downloader));
        parts.add(createAudioPart("6401936245564505757", new Interval(32+64, 44), returning, downloader));
        parts.add(createAudioPart("6182122145512625145", new Interval(64*2, 46), returning, downloader));
        parts.add(createAudioPart("3378726703924324403", new Interval(16+64*2, 30), returning, downloader));
        parts.add(createAudioPart("4823965795648964701", new Interval(14+32+64*2, 1), returning, downloader));
        parts.add(createAudioPart("5560598317419002938", new Interval(15+32+64*2, 1), returning, downloader));
        parts.add(createAudioPart("9040781467677187716", new Interval(16+32+64*2, 64), returning, downloader));
        parts.add(createAudioPart("8301899110835906945", new Interval(16+64*3, 16), scares_me, downloader));
        parts.add(createAudioPart("5555459205073513470", new Interval(32+64*3, 28), scares_me, downloader));
        return new Composition(getClass().getSimpleName(), masterBeatPattern, parts, TestMp3s.javaZoneComposition);
    }    
}
