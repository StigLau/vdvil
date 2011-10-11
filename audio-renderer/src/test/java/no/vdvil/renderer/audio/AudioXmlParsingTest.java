package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.lau.vdvil.cache.SimpleCacheImpl;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.persistence.*;
import no.lau.vdvil.timing.Interval;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AudioXmlParsingTest {

    DownloadAndParseFacade parseFacade = new DownloadAndParseFacade();
    AudioXMLParser audioXMLParser;

    @Before
    public void setup() {
        parseFacade.addCache(new SimpleCacheImpl());
        parseFacade.addParser(new CompositionXMLParser(parseFacade));
        audioXMLParser = new AudioXMLParser(parseFacade);
        parseFacade.addParser(audioXMLParser);
    }

    @Test
    public void audioXmlParsing() throws Exception {
        URL url = ClassLoader.getSystemResource("AudioExample.dvl.xml");

        String segmentId = "4336519975847252321";
        CompositionInstruction ci = new PartXML(segmentId, new Interval(-1, -1), new DvlXML("someDvl", url));
        AudioDescription audioDescription = audioXMLParser.parse(ci);
        audioDescription.cache(parseFacade);
        AudioInstruction audioInstruction = audioDescription.asInstruction(120F);
        assertNotNull(audioInstruction);
    }

    @Test
    public void compositionWithAudioParsing() throws IOException {
        String segmentId = "4479230163500364845";
        URL compositionUrl = TestMp3s.javaZoneComposition;
        CompositionInstruction ci = new PartXML(segmentId, new Interval(-1, -1), new DvlXML("someDvl", compositionUrl));
        Composition composition = (Composition) parseFacade.parse(ci);
        assertEquals(14, composition.multimediaParts.size());
    }
}
