package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.lau.IntegrationTest;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.persistence.*;
import no.lau.vdvil.timing.Interval;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.net.URL;

import static no.lau.NullChecker.nullChecked;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Category(IntegrationTest.class)
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
        URL url = nullChecked(ClassLoader.getSystemResource("audio/AudioExample.dvl.xml"));

        String segmentId = "4336519975847252321";
        CompositionInstruction ci = new PartXML(segmentId, new Interval(-1, 0), new DvlXML(Store.get().cache(url, "7a7051b2295481de6d741c83fe194708")));
        AudioDescription audioDescription = audioXMLParser.parse(ci);
        //Magic that "caches" the file
        audioDescription.updateFileRepresentation(Store.get().cache(audioDescription.fileRepresentation()));
        AudioInstruction audioInstruction = audioDescription.asInstruction(120F);
        assertNotNull(audioInstruction);
    }

    @Test
    @Ignore //Parsing fails
    public void compositionWithAudioParsing() throws IOException {
        String segmentId = "4479230163500364845";
        FileRepresentation compositionUrl = TestMp3s.javaZoneComposition;
        CompositionInstruction ci = new PartXML(segmentId, new Interval(-1, 0), new DvlXML(compositionUrl));
        Composition composition = (Composition) parseFacade.parse(ci);
        assertEquals(14, composition.multimediaParts.size());
    }
}
