package no.lau.vdvil.handler;

import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import org.junit.Test;

import java.net.URL;

public class CompositionTest {

    CompositionXMLParser xmlParser = new CompositionXMLParser();

    @Test
    public void testParsingComposition() throws Exception {
        //URL url = new URL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml");
        URL url = ClassLoader.getSystemResource("CompositionExample.xml");
        Composition composition = (Composition) xmlParser.parse(url.openStream());
        System.out.println("composition = " + composition);
    }
}
