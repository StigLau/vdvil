package no.lau.vdvil.handler;

import no.lau.vdvil.cache.SimpleCacheImpl;
import no.lau.vdvil.handler.persistence.*;
import org.junit.Test;

import java.net.URL;

public class CompositionTest {

    @Test
    public void testParsingComposition() throws Exception {
        DownloadAndParseFacade downloadAndParseFacade = new DownloadAndParseFacade();
        downloadAndParseFacade.addCache(new SimpleCacheImpl());
        downloadAndParseFacade.addParser(new CompositionXMLParser(downloadAndParseFacade));

        URL url = ClassLoader.getSystemResource("CompositionExample.xml");
        Composition composition = (Composition) downloadAndParseFacade.parse(PartXML.create(url));
        System.out.println("composition = " + composition);
    }
}
