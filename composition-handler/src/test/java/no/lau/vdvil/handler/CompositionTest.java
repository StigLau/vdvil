package no.lau.vdvil.handler;

import no.lau.vdvil.cache.SimpleCacheImpl;
import no.lau.vdvil.handler.persistence.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class CompositionTest {

    Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public void testParsingComposition() throws Exception {
        DownloadAndParseFacade downloadAndParseFacade = new DownloadAndParseFacade();
        downloadAndParseFacade.addCache(new SimpleCacheImpl());
        downloadAndParseFacade.addParser(new CompositionXMLParser(downloadAndParseFacade));

        URL url = ClassLoader.getSystemResource("CompositionExample.xml");
        Composition composition = (Composition) downloadAndParseFacade.parse(PartXML.create(url));
        log.info("composition = " + composition);
    }
}
