package no.lau.vdvil.renderer.image;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.instruction.ImageInstruction;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescription;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import org.junit.Test;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class CompositionWithImageParserTest {

    @Test
    public void compositionParsing() throws Exception {
        FileRepresentation fileRepresentation = Store.get().createKey(ClassLoader.getSystemResource("testCompositionWithImageDvls.xml"), "63d66578887d85accd3a6cb75c663b71");

        ParseFacade parser = new ParseFacade();
        parser.addParser(new ImageDescriptionXMLParser());
        parser.addParser(new CompositionXMLParser(parser));

        Composition composition = (Composition) parser.parse(PartXML.create(fileRepresentation));
        assertEquals("JavaZone Demo", composition.name);
        assert(composition.fileRepresentation().remoteAddress()).toString().endsWith("testCompositionWithImageDvls.xml");
        assertEquals(150, composition.masterBeatPattern.masterBpm.intValue());
        assertEquals(2, composition.multimediaParts.size());

        URL xmlImageUrl = new URL("http://farm3.static.flickr.com/2095/2282261838_276a37d325_o_d.jpg");

        assertEquals(ImageDescription.class, composition.multimediaParts.get(0).getClass());
        ImageDescription imageDescription = (ImageDescription) composition.multimediaParts.get(0);
        assertEquals(xmlImageUrl, imageDescription.fileRepresentation().remoteAddress());
        ImageInstruction imageInstruction = imageDescription.asInstruction(120F);
        assertEquals(xmlImageUrl, imageInstruction.fileRepresentation().remoteAddress());
    }
}
