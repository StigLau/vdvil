package no.lau.vdvil.handler;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.persistence.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CompositionTest {

    @Test
    public void testParsingComposition() throws Exception {
        ParseFacade parseFacade = new ParseFacade();
        parseFacade.addParser(new CompositionXMLParser(parseFacade));

        FileRepresentation fileRepresentation = Store.get().createKey(ClassLoader.getSystemResource("CompositionExample.xml"), "33b7c142b2761a9a8cc0e6839b2b83d1");
        Composition composition = (Composition) parseFacade.parse(PartXML.create(fileRepresentation));
        assertEquals("A simple example", composition.name);
        assertEquals(150, composition.masterBeatPattern.masterBpm.intValue());
        assertEquals(0, composition.masterBeatPattern.fromBeat.intValue());
        assertEquals(64, composition.masterBeatPattern.toBeat.intValue());
    }
}
