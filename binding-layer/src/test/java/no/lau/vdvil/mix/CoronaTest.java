package no.lau.vdvil.mix;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.mix.util.CompositionHelper;
import no.lau.vdvil.playback.BackStage;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.util.Arrays;
import java.util.List;

@Tag("IntegrationTest")
public class CoronaTest {
    final Track corona = TestMp3s.corona;
    final MasterBeatPattern mbp = new MasterBeatPattern(0, 32, 150F);

    @Test
    public void play() {
        new BackStage().prepare(composition).playUntilEnd();
    }

    final Composition composition = new Composition(getClass().getSimpleName(), mbp, FileRepresentation.NULL, new CompositionHelper() {
        public List<MultimediaPart> parts() {
            return Arrays.asList(
                    createPart(new Interval(0, 20), corona.segments.get(3), corona, corona.mediaFile.fileName),
                    createPart(new Interval(4, 4), corona.segments.get(6), corona, corona.mediaFile.fileName));
        }
    });
}