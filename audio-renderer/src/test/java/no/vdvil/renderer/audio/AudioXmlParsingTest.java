package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.persistence.*;
import no.lau.vdvil.timing.Interval;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AudioXmlParsingTest {

    ParseFacade parseFacade;
    AudioXMLParser audioXMLParser;

    @Before
    public void setup() {
        parseFacade = new ParseFacade();
        parseFacade.addParser(new CompositionXMLParser(parseFacade));
        audioXMLParser = new AudioXMLParser();
        parseFacade.addParser(audioXMLParser);
    }

    @Test
    public void audioXmlParsing() throws Exception {
        URL url = ClassLoader.getSystemResource("AudioExample.dvl.xml");

        String segmentId = "4336519975847252321";
        CompositionInstruction ci = new PartXML(segmentId, new Interval(-1, 0), new DvlXML(Store.get().cache(url, "2e24054eb28edd38c9a846022587955b")));
        AudioDescription audioDescription = audioXMLParser.parse(ci);
        //Magic that "caches" the file
        audioDescription.updateFileRepresentation(Store.get().cache(audioDescription.fileRepresentation()));
        AudioInstruction audioInstruction = audioDescription.asInstruction(120F);
        assertNotNull(audioInstruction);
    }

    @Test
    public void compositionWithAudioParsing() throws IOException {
        String segmentId = "4479230163500364845";
        URL compositionUrl = TestMp3s.javaZoneComposition;
        CompositionInstruction ci = new PartXML(segmentId, new Interval(-1, 0), new DvlXML("someDvl", compositionUrl));
        Composition composition = (Composition) parseFacade.parse(ci);
        assertEquals(14, composition.multimediaParts.size());
    }
}
