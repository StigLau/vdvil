package no.lau.vdvil.parser.json;

import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.handler.persistence.domain.CompositionXml;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URL;
import static org.junit.Assert.assertEquals;

/**
 * Convinience class for converting dvl xmls to json
 */
public class ConvertXmlDvlsToJson {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testConvertingStaticXmlFile() throws IOException {
        URL url = this.getClass().getResource("/CompositionExample.xml");
        String json = convertToJson(url);
        logger.info(json);
        assertEquals("{\"name\":\"A simple example\",\"masterBpm\":150.0,\"parts\":[{\"id\":\"1\",\"start\":0,\"end\":32,\"cueDifference\":0,\"dvl\":{\"name\":\"yey\",\"url\":\"http://dvl.lau.no/dvls/yey.dvl\"}},{\"id\":\"2\",\"start\":32,\"end\":64,\"cueDifference\":0,\"dvl\":{\"name\":\"yey\",\"url\":\"http://dvl.lau.no/dvls/yey.dvl\"}}]}", json);
    }

    public String convertToJson(URL xmlFile) throws IOException {
        CompositionXml compositionXml = CompositionXMLParser.parseCompositionXml(xmlFile.openStream());
        return CompositionJsonParser.convertCompositionToJson(compositionXml);
    }
}