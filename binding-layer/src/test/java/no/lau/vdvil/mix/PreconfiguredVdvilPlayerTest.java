package no.lau.vdvil.mix;

import no.bouvet.kpro.renderer.Instruction;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PreconfiguredVdvilPlayerTest {
    MasterBeatPattern filter = new MasterBeatPattern(4, 8, -1F);

    @Test
    public void removedByFilter() {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(instructionDummy("Ends BeforeFilter", 0, 4));
        parts.add(instructionDummy("AfterEnd", 12, 18));
        Composition filteredComposition = PreconfiguredVdvilPlayer.filterByTime(createComposition(parts), filter);
        assertEquals(0, filteredComposition.multimediaParts.size());
    }
    @Test
    public void croppedInBothEnds() {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(instructionDummy("LoongSectionNeedsToBeClippedInBothEnds", 2, 17));
        Composition filteredComposition = PreconfiguredVdvilPlayer.filterByTime(createComposition(parts), filter);
        CompositionInstruction cropped = filteredComposition.multimediaParts.get(0).compositionInstruction();
        assertEquals(4, cropped.start());
        assertEquals(8, cropped.end());
    }

    @Test
    public void croppingStart() {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(instructionDummy("StartClippedByFilter", 2, 6));
        Composition filteredComposition = PreconfiguredVdvilPlayer.filterByTime(createComposition(parts), filter);
        CompositionInstruction cropped = filteredComposition.multimediaParts.get(0).compositionInstruction();
        assertEquals(4, cropped.start());
        assertEquals(6, cropped.end());
    }
    @Test
    public void croppingEnd() {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(instructionDummy("EndClippedByFilter", 6, 10));
        Composition filteredComposition = PreconfiguredVdvilPlayer.filterByTime(createComposition(parts), filter);
        CompositionInstruction cropped = filteredComposition.multimediaParts.get(0).compositionInstruction();
        assertEquals(6, cropped.start());
        assertEquals(8, cropped.end());
    }

    @Test
    public void notFiltered() {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(instructionDummy("InsideFilter", 4, 8));
        parts.add(instructionDummy("SpaciouslyInsideFilter", 5, 7));
        Composition filteredComposition = PreconfiguredVdvilPlayer.filterByTime(createComposition(parts), filter);
        assertEquals(2, filteredComposition.multimediaParts.size());
    }

    private MultimediaPart instructionDummy(final String id, final int start, final int end) {
        return new MultimediaPart() {
            CompositionInstruction compositionInstruction = new PartXML(id, start, end, null);
            public Instruction asInstruction(Float masterBpm) { return null; }
            public CompositionInstruction compositionInstruction() {
                return compositionInstruction;
            }
            public void cache(DownloadAndParseFacade downloader) throws IOException {}
        };
    }
    private Composition createComposition(List<MultimediaPart> parts) {
        return new Composition("", null, parts, null);
    }
}