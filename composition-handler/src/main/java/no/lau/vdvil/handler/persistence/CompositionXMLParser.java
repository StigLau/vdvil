package no.lau.vdvil.handler.persistence;

import com.thoughtworks.xstream.XStream;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.MultimediaPart;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CompositionXMLParser implements MultimediaParser {

    XStream xstream = new XStream();

    public CompositionXMLParser() {
        xstream.alias("composition", CompositionXML.class);
        xstream.alias("part", PartXML.class);
        xstream.alias("dvl", DvlXML.class);
    }

    public MultimediaPart parse(InputStream inputStream) throws Exception {
        return convert((CompositionXML) xstream.fromXML(inputStream));
    }

    //TODO WHAT IF the multimediaParts can't be parsed by XStream?
    public Composition convert(CompositionXML cXML) {

        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        for (final PartXML partXML : cXML.parts) {
            parts.add(new MultimediaPart() {
                public int getStartCue() { return partXML.start; }
                public int getEndCue() { return partXML.end; }
                public URL getUrl() { return partXML.dvl.url; }
            });
        }
        return new Composition(cXML.name, cXML.masterBpm, parts, null);
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