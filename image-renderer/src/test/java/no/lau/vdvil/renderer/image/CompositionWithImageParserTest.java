package no.lau.vdvil.renderer.image;

import no.lau.vdvil.cache.SimpleVdvilCache;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.handler.persistence.SimpleFileCache;
import no.vdvil.renderer.image.ImageInstruction;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescription;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import org.junit.Test;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class CompositionWithImageParserTest {

    SimpleVdvilCache cache = new SimpleFileCache();

    @Test
    public void compositionParsing() throws Exception {
        URL compositionXmlUrl = getClass().getResource("/testCompositionWithImageDvls.xml");

        DownloadAndParseFacade facade = new DownloadAndParseFacade();
        facade.addParser(new ImageDescriptionXMLParser(cache));
        facade.addParser(new CompositionXMLParser(facade));
        facade.addCache(cache);
        //facade.addCache(VdvilHttpCache.create());

        Composition composition = (Composition) facade.parse("", compositionXmlUrl);
        assertEquals("JavaZone Demo", composition.name);
        assert(composition.url.toString().endsWith("testCompositionWithImageDvls.xml"));
        assertEquals(150, composition.masterBpm.intValue());
        assertEquals(1, composition.multimediaParts.size());

        URL xmlImageUrl = new URL("http://farm3.static.flickr.com/2095/2282261838_276a37d325_o_d.jpg");

        assertEquals(ImageDescription.class,  composition.multimediaParts.get(0).getClass());
        ImageDescription imageDescription = (ImageDescription) composition.multimediaParts.get(0);
        assertEquals(xmlImageUrl, imageDescription.src);
        ImageInstruction imageInstruction = imageDescription.asInstruction(120F);
        assertEquals(xmlImageUrl, imageInstruction.imageUrl);
    }
}
