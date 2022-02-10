package no.lau.vdvil.parser.json;

import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.handler.persistence.domain.CompositionXml;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URL;
import static no.lau.NullChecker.nullChecked;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Convinience class for converting dvl xmls to json
 */
public class ConvertXmlDvlsToJson {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testConvertingStaticXmlFile() throws IOException {
        URL url = nullChecked(getClass().getResource("/CompositionExample.xml"));
        String json = convertToJson(url);
        logger.info(json);
        assertEquals("{\"name\":\"A simple example\",\"masterBpm\":150.0,\"parts\":[{\"id\":\"1\",\"start\":0,\"end\":32,\"dvl\":{\"name\":\"yey\",\"url\":\"https://s3.amazonaws.com/dvl-test-music/dvl/loaderror-psylteflesk.dvl\"}},{\"id\":\"2\",\"start\":32,\"end\":64,\"dvl\":{\"name\":\"yey2\",\"url\":\"https://s3.amazonaws.com/dvl-test-music/dvl/holden-nothing-93_returning_mix.dvl\"}}]}", json);
    }

    public String convertToJson(URL xmlFile) throws IOException {
        CompositionXml compositionXml = CompositionXMLParser.parseCompositionXml(xmlFile.openStream());
        return CompositionJsonParser.convertCompositionToJson(compositionXml);
    }
}