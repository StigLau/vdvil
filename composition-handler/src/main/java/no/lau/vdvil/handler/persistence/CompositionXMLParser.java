package no.lau.vdvil.handler.persistence;

import com.thoughtworks.xstream.XStream;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.persistence.domain.CompositionXml;
import no.lau.vdvil.timing.Interval;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CompositionXMLParser implements MultimediaParser{

    static final XStream xstream;
    ParseFacade parser;
    Store store;

    public CompositionXMLParser(ParseFacade parser) {
        this.parser = parser;
        this.store = Store.get();
    }
    static {
        xstream = new XStream();
        xstream.alias("composition", CompositionXml.class);
        xstream.alias("part", OldPart.class);
        xstream.alias("dvl", DvlXML.class);
        xstream.allowTypesByWildcard(new String[] {
                "no.lau.vdvil.handler.persistence.domain.CompositionXml",
                "no.lau.vdvil.handler.persistence.OldPart",
                "no.lau.vdvil.handler.persistence.DvlXML"
        });
    }

    @Deprecated // Could instead use parse(URL)
    public Composition parse(CompositionInstruction compositionInstruction) throws IOException {
        MultimediaReference dvl = compositionInstruction.dvl();
        FileRepresentation fileRepresentation = store.cache(dvl.url(), dvl.fileChecksum());
        CompositionXml compositionXml = convert(parseCompositionXml(new FileInputStream(fileRepresentation.localStorage())));

        //Convert
        return compositionXml.asComposition(fileRepresentation, parser);
    }

    /**
     * Ugly method for converting OlParts to PartXML because of API change
     */
    private CompositionXml convert(CompositionXml old) {
        ArrayList<PartXML> newParts = new ArrayList<>();
        List<PartXML> oldparts = old.parts;
        for (Object oldpart : oldparts) {
            OldPart ol = (OldPart)oldpart;
            newParts.add(new PartXML(ol.id, new Interval(ol.start, ol.end - ol.start), ol.dvl));
        }
        old.parts = newParts;
        return old;
    }

    public static CompositionXml parseCompositionXml(InputStream stream) {
        return (CompositionXml) xstream.fromXML(stream);
    }
}

/**
 * Similar to PartXML. Only used by XStream
 */
class OldPart {
    String id;
    int start;
    int end;
    DvlXML dvl;
}