package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.persistence.*;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AudioXmlParsingTest {

    @Test
    public void audioXmlParsing() throws Exception {
        URL url = ClassLoader.getSystemResource("AudioExample.dvl.xml");
        AudioXMLParser audioXMLParser = new AudioXMLParser();
        String segmentId = "4336519975847252321";
        CompositionInstruction ci = new PartXML(segmentId, -1, -1, new DvlXML("someDvl", url));

        DownloadAndParseFacade parseFacade = new DownloadAndParseFacade();
        parseFacade.addCache(VdvilHttpCache.create());
        parseFacade.addCache(new SimpleFileCache());
        audioXMLParser.setDownloaderAndParseFacade(parseFacade);

        AudioDescription audioDescription = audioXMLParser.parse(ci);
        audioDescription.cache(parseFacade);
        AudioInstruction audioInstruction = audioDescription.asInstruction(120F);
        assertNotNull(audioInstruction);
    }

    @Test
    public void compositionWithAudioParsing() throws IOException {
        String segmentId = "4479230163500364845";
        URL compositionUrl = TestMp3s.javaZoneComposition;
        CompositionInstruction ci = new PartXML(segmentId, -1, -1, new DvlXML("someDvl", compositionUrl));

        DownloadAndParseFacade parseFacade = new DownloadAndParseFacade();
        parseFacade.addCache(VdvilHttpCache.create());
        parseFacade.addCache(new SimpleFileCache());
        parseFacade.addParser(new CompositionXMLParser(parseFacade));
        parseFacade.addParser(new AudioXMLParser(parseFacade));

        Composition composition = (Composition) parseFacade.parse(ci);
        assertEquals(14, composition.multimediaParts.size());
    }
}
