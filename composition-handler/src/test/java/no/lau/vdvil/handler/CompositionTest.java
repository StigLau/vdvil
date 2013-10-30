package no.lau.vdvil.handler;

import no.lau.vdvil.handler.persistence.*;
import org.junit.Test;
import java.net.URL;
import static org.junit.Assert.assertEquals;

public class CompositionTest {


    @Test
    public void testParsingComposition() throws Exception {
        ParseFacade parseFacade = new ParseFacade();
        parseFacade.addParser(new CompositionXMLParser(parseFacade));

        URL url = ClassLoader.getSystemResource("CompositionExample.xml");
        Composition composition = (Composition) parseFacade.parse(PartXML.create(url));
        assertEquals("A simple example", composition.name);
        assertEquals(150, composition.masterBeatPattern.masterBpm.intValue());
        assertEquals(0, composition.masterBeatPattern.fromBeat.intValue());
        assertEquals(64, composition.masterBeatPattern.toBeat.intValue());
    }
}
