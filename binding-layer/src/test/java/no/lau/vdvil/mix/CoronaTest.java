package no.lau.vdvil.mix;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.mix.util.SuperPlayingSetup;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.*;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoronaTest {
    Track corona = TestMp3s.corona;

    @Test
    public void play() {
        new SuperPlayingSetup() {
            @Override
            public Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
                List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
                parts.add(createPart(new Interval(0, 20), corona.segments.get(3), corona, corona.mediaFile.fileName));
                parts.add(createPart(new Interval(4, 4), corona.segments.get(6), corona, corona.mediaFile.fileName));
                return new Composition(getClass().getSimpleName(), masterBeatPattern, parts, FileRepresentation.NULL); //Error, can't handle NULL Filerepresentation
            }
        }.play(new MasterBeatPattern(0, 32, 150F));
    }
}