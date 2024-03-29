package no.lau.vdvil.handler;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.persistence.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Tag("Deprecated")
public class CompositionTest {

    @Test
    public void testParsingComposition() throws Exception {
        ParseFacade parseFacade = new ParseFacade();
        parseFacade.addParser(new CompositionXMLParser(parseFacade));

        FileRepresentation fileRepresentation = Store.get().createKey(ClassLoader.getSystemResource("CompositionExample.xml"), "82058797b8edfdbe5fac1218806a175c");
        Composition composition = (Composition) parseFacade.parse(PartXML.create(fileRepresentation));
        assertEquals("A simple example", composition.name);
        assertEquals(150, composition.masterBeatPattern.masterBpm.intValue());
        assertEquals(0, composition.masterBeatPattern.fromBeat.intValue());
        assertEquals(64, composition.masterBeatPattern.toBeat.intValue());
    }
}
