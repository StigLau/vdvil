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
        AudioDescription audioDescription = audioXMLParser.parse(url);

        AudioInstruction audioInstruction = audioDescription.asInstruction("4336519975847252321", 1, 2, 3, 120F, 3F);
        assertNotNull(audioInstruction);
    }

    @Test
    public void compositionWithAudioParsing() throws IOException {
        URL compositionUrl = new URL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml");
        DownloadAndParseFacade parseFacade = new DownloadAndParseFacade();
        parseFacade.addCache(VdvilHttpCache.create());
        parseFacade.addCache(new SimpleFileCache());
        parseFacade.addParser(new CompositionXMLParser(parseFacade));
        parseFacade.addParser(new AudioXMLParser(parseFacade));

        Composition composition = (Composition) parseFacade.parse(compositionUrl);
        assertEquals(14, composition.multimediaParts.size());
    }
}
