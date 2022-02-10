package no.lau.vdvil.mix;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.playback.BackStage;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.lau.vdvil.timing.TimeInterval;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VdvilAudioConfigTest {
    final MasterBeatPattern filter = new MasterBeatPattern(4, 8, -1F);

    @Test
    public void removedByFilter() {
        List<MultimediaPart> parts = new ArrayList<>();
        parts.add(instructionDummy("Ends BeforeFilter", new Interval(0, 4)));
        parts.add(instructionDummy("AfterEnd", new Interval(12, 6)));
        Composition filteredComposition = BackStage.filterByTime(createComposition(parts), filter);
        assertEquals(0, filteredComposition.multimediaParts.size());
    }
    @Test
    public void croppedInBothEnds() {
        List<MultimediaPart> parts = new ArrayList<>();
        parts.add(instructionDummy("LoongSectionNeedsToBeClippedInBothEnds", new Interval(2, 15)));
        Composition filteredComposition = BackStage.filterByTime(createComposition(parts), filter);
        CompositionInstruction cropped = filteredComposition.multimediaParts.get(0).compositionInstruction();
        assertEquals(4, cropped.start());
        assertEquals(8, cropped.end());
    }

    @Test
    public void croppingStart() {
        List<MultimediaPart> parts = new ArrayList<>();
        parts.add(instructionDummy("StartClippedByFilter", new Interval(2, 4)));
        Composition filteredComposition = BackStage.filterByTime(createComposition(parts), filter);
        CompositionInstruction cropped = filteredComposition.multimediaParts.get(0).compositionInstruction();
        assertEquals(4, cropped.start());
        assertEquals(6, cropped.end());
    }
    @Test
    public void croppingEnd() {
        List<MultimediaPart> parts = new ArrayList<>();
        parts.add(instructionDummy("EndClippedByFilter", new Interval(6, 4)));
        Composition filteredComposition = BackStage.filterByTime(createComposition(parts), filter);
        CompositionInstruction cropped = filteredComposition.multimediaParts.get(0).compositionInstruction();
        assertEquals(6, cropped.start());
        assertEquals(8, cropped.end());
    }

    @Test
    public void notFiltered() {
        List<MultimediaPart> parts = new ArrayList<>();
        parts.add(instructionDummy("InsideFilter", new Interval(4, 4)));
        parts.add(instructionDummy("SpaciouslyInsideFilter", new Interval(5, 2)));
        Composition filteredComposition = BackStage.filterByTime(createComposition(parts), filter);
        assertEquals(2, filteredComposition.multimediaParts.size());
    }

    private MultimediaPart instructionDummy(final String id, final TimeInterval timeInterval) {
        return new MultimediaPart() {
            final CompositionInstruction compositionInstruction = new PartXML(id, timeInterval, null);
            public Instruction asInstruction(Float masterBpm) { return null; }

            public no.lau.vdvil.instruction.Instruction asV2Instruction() {
                throw new RuntimeException("Does not need this");
            }

            public CompositionInstruction compositionInstruction() {
                return compositionInstruction;
            }

            public FileRepresentation fileRepresentation() {
                return FileRepresentation.NULL;
            }
        };
    }
    private Composition createComposition(List<MultimediaPart> parts) {
        return new Composition("", null, null, () -> parts);
    }
}
