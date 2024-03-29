package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.persistence.*;
import no.lau.vdvil.timing.Interval;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("IntegrationTest")
public class AudioXmlParsingTest {

    ParseFacade parseFacade;
    AudioXMLParser audioXMLParser;

    @BeforeEach
    public void setup() {
        parseFacade = new ParseFacade();
        parseFacade.addParser(new CompositionXMLParser(parseFacade));
        audioXMLParser = new AudioXMLParser();
        parseFacade.addParser(audioXMLParser);
    }

    @Test
    public void audioXmlParsing() throws Exception {
        URL url = ClassLoader.getSystemResource("audio/AudioExample.dvl.xml");
        assertNotNull(url);
        String segmentId = "4336519975847252321";
        CompositionInstruction ci = new PartXML(segmentId, new Interval(-1, 0), new DvlXML(Store.get().cache(url, "a11234ac9dfcc7e0d0de1489fcd8d9ad")));
        AudioDescription audioDescription = audioXMLParser.parse(ci);
        //Magic that "caches" the file
        audioDescription.updateFileRepresentation(Store.get().cache(audioDescription.fileRepresentation()));
        AudioInstruction audioInstruction = audioDescription.asInstruction(120F);
        assertNotNull(audioInstruction);
    }

    @Test
    @Disabled //Parsing fails
    public void compositionWithAudioParsing() throws IOException {
        String segmentId = "4479230163500364845";
        FileRepresentation compositionUrl = TestMp3s.javaZoneComposition;
        CompositionInstruction ci = new PartXML(segmentId, new Interval(-1, 0), new DvlXML(compositionUrl));
        Composition composition = (Composition) parseFacade.parse(ci);
        assertEquals(14, composition.multimediaParts.size());
    }
}
