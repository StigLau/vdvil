package no.lau.vdvil.handler;

import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.handler.persistence.SimpleFileCache;
import org.junit.Test;

import java.net.URL;

public class CompositionTest {

    @Test
    public void testParsingComposition() throws Exception {
        DownloadAndParseFacade downloadAndParseFacade = new DownloadAndParseFacade();
        downloadAndParseFacade.addCache(new SimpleFileCache());
        downloadAndParseFacade.addParser(new CompositionXMLParser(downloadAndParseFacade));

        URL url = ClassLoader.getSystemResource("CompositionExample.xml");
        Composition composition = (Composition) downloadAndParseFacade.parse("", url);
        System.out.println("composition = " + composition);
    }
}