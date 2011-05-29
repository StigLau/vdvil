package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.handler.persistence.SimpleFileCache;
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

        DownloadAndParseFacade parseFacade = new DownloadAndParseFacade();
        parseFacade.addCache(VdvilHttpCache.create());
        parseFacade.addCache(new SimpleFileCache());
        audioXMLParser.setDownloaderAndParseFacade(parseFacade);
        String segmentId = "4336519975847252321";
        AudioDescription audioDescription = audioXMLParser.parse(segmentId, url);

        AudioInstruction audioInstruction = audioDescription.asInstruction(0, 16, 120F);
        assertNotNull(audioInstruction);
    }

    @Test
    public void compositionWithAudioParsing() throws IOException {
        String segmentId = "4479230163500364845";
        URL compositionUrl = new URL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml");
        DownloadAndParseFacade parseFacade = new DownloadAndParseFacade();
        parseFacade.addCache(VdvilHttpCache.create());
        parseFacade.addCache(new SimpleFileCache());
        parseFacade.addParser(new CompositionXMLParser(parseFacade));
        parseFacade.addParser(new AudioXMLParser(parseFacade));

        Composition composition = (Composition) parseFacade.parse(segmentId, compositionUrl);
        assertEquals(14, composition.multimediaParts.size());
    }
}
