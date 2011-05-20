package no.lau.vdvil.handler;

import no.lau.vdvil.cache.SimpleVdvilCache;
import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.handler.persistence.SimpleFileCache;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompositionTest {

    @Test
    public void testParsingComposition() throws Exception {
        List<SimpleVdvilCache> caches = new ArrayList<SimpleVdvilCache>();
        caches.add(new SimpleFileCache());
        List<MultimediaParser> parsers = Collections.emptyList();
        CompositionXMLParser xmlParser = new CompositionXMLParser(caches, parsers);

        //URL url = new URL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml");
        URL url = ClassLoader.getSystemResource("CompositionExample.xml");
        Composition composition = xmlParser.parse(url);
        System.out.println("composition = " + composition);
    }
}
