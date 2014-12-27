package no.lau.vdvil.playback;

import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.timing.Interval;
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
        partList.add(new ImageDescription(new PartXML("0 14", new Interval(0, 14), null), null));
        partList.add(new ImageDescription(new PartXML("33 34", new Interval(33, 1), null), null));
        partList.add(new ImageDescription(new PartXML("18 1", new Interval(18, 1), null), null));
        Composition testComposition = new Composition("", new MasterBeatPattern(0, 120, 120F), null, () -> partList);

        Composition result = BackStage.filterByTime(testComposition, new MasterBeatPattern(16, 32, 130F));
        assertEquals(1, result.multimediaParts.size());
        assertEquals("18 1", result.multimediaParts.get(0).compositionInstruction().id());
        assertEquals(19, result.multimediaParts.get(0).compositionInstruction().end());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartOkSmallEnd() {
        new PartXML("17 -1", new Interval(17, -1), null);
    }

    @Test
    public void testSegmentStartsAndStopsOutsideOfWindow() {
        List<MultimediaPart> partList = new ArrayList<MultimediaPart>();
        partList.add(new ImageDescription(new PartXML("0 20", new Interval(0, 20), null), null));
        Composition testComposition = new Composition("", new MasterBeatPattern(2, 10, 120F), null, () -> partList);

        Composition result = BackStage.filterByTime(testComposition, new MasterBeatPattern(4, 8, 130F));
        assertEquals(4, result.multimediaParts.get(0).compositionInstruction().start());
        assertEquals(8, result.multimediaParts.get(0).compositionInstruction().end());
    }
}
