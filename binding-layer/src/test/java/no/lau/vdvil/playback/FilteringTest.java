package no.lau.vdvil.playback;

import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescription;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Test that filtering is performed correctly
 */
public class FilteringTest {
    @Test
    public void testSettingValueIfEndTimeAfterStart() {
        List<MultimediaPart> partList = new ArrayList<MultimediaPart>();
        partList.add(new ImageDescription(new PartXML("0 16", 0, 14, null), null));
        partList.add(new ImageDescription(new PartXML("0 -1", 0, -1, null), null));
        partList.add(new ImageDescription(new PartXML("33 -1", 33, -1, null), null));
        partList.add(new ImageDescription(new PartXML("33 34", 33, 34, null), null));
        partList.add(new ImageDescription(new PartXML("18 17", 18, 17, null), null));
        Composition testComposition = new Composition("", new MasterBeatPattern(0, 120, 120F), partList, null);

        Composition result = PreconfiguredVdvilPlayer.filterByTime(testComposition, new MasterBeatPattern(16, 32, 130F));
        assertEquals(2, result.multimediaParts.size());
        assertEquals("0 -1", result.multimediaParts.get(0).compositionInstruction().id());
        assertEquals(32, result.multimediaParts.get(0).compositionInstruction().end());
        assertEquals("18 17", result.multimediaParts.get(1).compositionInstruction().id());
        assertEquals(32, result.multimediaParts.get(1).compositionInstruction().end());
    }

    @Test
    public void testStartOkSmallEnd() {
        List<MultimediaPart> partList = new ArrayList<MultimediaPart>();
        partList.add(new ImageDescription(new PartXML("17 -1", 17, -1, null), null));
        Composition testComposition = new Composition("", new MasterBeatPattern(0, 120, 120F), partList, null);

        Composition result = PreconfiguredVdvilPlayer.filterByTime(testComposition, new MasterBeatPattern(16, 32, 130F));
        assertEquals(32, result.multimediaParts.get(0).compositionInstruction().end());
    }

    @Test
    public void testStartOkSmallEndCapped() {
        List<MultimediaPart> partList = new ArrayList<MultimediaPart>();
        partList.add(new ImageDescription(new PartXML("17 -1", 17, -1, null), null));
        Composition testComposition = new Composition("", new MasterBeatPattern(0, 120, 120F), partList, null);

        Composition result = PreconfiguredVdvilPlayer.filterByTime(testComposition, new MasterBeatPattern(16, 1020, 130F));
        assertEquals(1017, result.multimediaParts.get(0).compositionInstruction().end());
    }
}
