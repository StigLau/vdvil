package no.lau.vdvil.handler.persistence;

import com.thoughtworks.xstream.XStream;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.persistence.domain.CompositionXml;
import java.io.IOException;
import java.io.InputStream;

public class CompositionXMLParser implements MultimediaParser{

    static XStream xstream;
    ParseFacade parser;
    Store store;

    public CompositionXMLParser(ParseFacade parser) {
        this.parser = parser;
        this.store = Store.get();
    }
    static {
        xstream = new XStream();
        xstream.alias("composition", CompositionXml.class);
        xstream.alias("part", PartXML.class);
        xstream.alias("dvl", DvlXML.class);
    }

    @Deprecated // Could instead use parse(URL)
    public Composition parse(CompositionInstruction compositionInstruction) throws IOException {
        FileRepresentation fileRepresentation = store.cache(compositionInstruction.dvl().url());
        CompositionXml compositionXml = parseCompositionXml(fileRepresentation.localStorage().openStream());
        return compositionXml.asComposition(fileRepresentation, parser);
    }

    public static CompositionXml parseCompositionXml(InputStream stream) throws IOException {
        return (CompositionXml) xstream.fromXML(stream);
    }
}
