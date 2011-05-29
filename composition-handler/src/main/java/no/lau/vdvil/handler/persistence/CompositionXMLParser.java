package no.lau.vdvil.handler.persistence;

import com.thoughtworks.xstream.XStream;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.MultimediaPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CompositionXMLParser implements MultimediaParser{

    Logger log = LoggerFactory.getLogger(getClass());

    XStream xstream;
    DownloadAndParseFacade downloadAndParseFacade;

    public CompositionXMLParser(DownloadAndParseFacade facade) {
        downloadAndParseFacade = facade;
        xstream = new XStream();
        xstream.alias("composition", CompositionXML.class);
        xstream.alias("part", PartXML.class);
        xstream.alias("dvl", DvlXML.class);
    }

    public Composition parse(String id, URL url) throws IOException {
        return convert((CompositionXML) xstream.fromXML(downloadAndParseFacade.fetchAsStream(url)), url);
    }

    public Composition convert(CompositionXML cXML, URL url) {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        for (final PartXML partXML : cXML.parts) {
            try{
                parts.add(downloadAndParseFacade.parse(partXML.id, partXML.dvl.url));
            } catch (IOException e) {
                log.error("Unable to parse or download {}", partXML.dvl.name);
            }
        }
        return new Composition(cXML.name, cXML.masterBpm, parts, url);
    }
}

/**
 * Internal classes only used for parsing XML!
 */
class CompositionXML {
    String name;
    Float masterBpm;
    List<PartXML> parts;
}

class PartXML {
    String id;
    int start;
    int end;
    DvlXML dvl;
}

class DvlXML {
    String name;
    URL url;
}