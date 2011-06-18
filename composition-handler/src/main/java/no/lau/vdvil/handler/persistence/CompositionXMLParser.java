package no.lau.vdvil.handler.persistence;

import com.thoughtworks.xstream.XStream;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.timing.MasterBeatPattern;
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

    public Composition parse(CompositionInstruction compositionInstruction) throws IOException {
        URL url = compositionInstruction.dvl().url();
        return convert((CompositionXML) xstream.fromXML(downloadAndParseFacade.fetchAsStream(url)), url);
    }

    public Composition convert(CompositionXML cXML, URL url) {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        int beatLength = 0;
        for (final PartXML partXML : cXML.parts) {
            if(partXML.end() > beatLength)
                beatLength = partXML.end();
            try{
                parts.add(downloadAndParseFacade.parse(partXML));
            } catch (IOException e) {
                log.error("Unable to parse or download {}", partXML.dvl.name);
            }
        }
        return new Composition(cXML.name, new MasterBeatPattern(0, beatLength, cXML.masterBpm), parts, url);
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

