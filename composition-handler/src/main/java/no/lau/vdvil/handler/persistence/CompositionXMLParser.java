package no.lau.vdvil.handler.persistence;

import com.thoughtworks.xstream.XStream;
import no.lau.vdvil.cache.SimpleVdvilCache;
import no.lau.vdvil.handler.AbstractMultimediaParser;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.MultimediaPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CompositionXMLParser extends AbstractMultimediaParser{

    Logger log = LoggerFactory.getLogger(getClass());

    XStream xstream = new XStream();

    public CompositionXMLParser(List<? extends SimpleVdvilCache> caches, List<? extends MultimediaParser> parsers) {
        super(caches, parsers);
        xstream.alias("composition", CompositionXML.class);
        xstream.alias("part", PartXML.class);
        xstream.alias("dvl", DvlXML.class);
    }

    public Composition parse(URL url) throws IOException {
        return convert((CompositionXML) xstream.fromXML(fetchAsStream(url)), url);
    }


    public Composition convert(CompositionXML cXML, URL url) {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        for (final PartXML partXML : cXML.parts) {
            MultimediaPart mpart = parseDvl(partXML.dvl);
            if(mpart != MultimediaPart.NULL) {
                parts.add(mpart);
            }
        }
        return new Composition(cXML.name, cXML.masterBpm, parts, url);
    }

    /**
     * Tries to download a DVL and see if a Parser will create a MultimediaPart from it.
     * @param dvl with URL
     * @return a parsed MultimediaPart. MultimediaPart.NULL if unsuccessful
     */
    MultimediaPart parseDvl(DvlXML dvl){
        for (MultimediaParser parser : parsers) {
            try {
                return parser.parse(dvl.url);
            } catch (Exception e) {
                log.error("Parser {} could not parse {}", new Object[]{parser, dvl.url}, e);
            }
        }
        log.error("Not able to parse {} located at {}, check debug log!", dvl.name, dvl.url);
        return MultimediaPart.NULL;
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