package no.lau.vdvil.parser.json;

import org.junit.Test;
import java.io.InputStreamReader;
import static org.junit.Assert.assertEquals;

public class CompositionJsonParserTest {

    @Test
    public void testParsingComposition() throws Exception {
        InputStreamReader reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream("CompositionExample.dvl.json"));

        CompositionSerializedJson comp = CompositionJsonParser.parseJsonStringToTrack(reader);
        assertEquals(new Float(150), comp.masterBpm);
        assertEquals("A simple example", comp.name);
        PartJson part0 = comp.parts.get(0);
        assertEquals("id1", part0.id());
        assertEquals(0, part0.start());
        assertEquals(32, part0.duration());

        PartJson part1 = comp.parts.get(1);
        assertEquals("id2", part1.id());
        assertEquals(32, part1.start());
        assertEquals(32, part1.duration()
        );
    }
}
