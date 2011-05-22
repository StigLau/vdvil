package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.persistence.SimpleFileCache;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import org.junit.Test;
import java.net.URL;

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
}
