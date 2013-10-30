package no.lau.vdvil.parser.json;

import com.google.gson.Gson;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.handler.persistence.DvlXML;
import no.lau.vdvil.handler.persistence.domain.CompositionXml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.URL;

public class CompositionJsonParser implements MultimediaParser {

    final ParseFacade parser;
    Store store;
    static Gson jsonParser = new Gson();
    Logger log = LoggerFactory.getLogger(getClass());

    public CompositionJsonParser(ParseFacade parser) {
        this.parser = parser;
        this.store = Store.get();
    }

    public MultimediaPart parse(CompositionInstruction compositionInstruction) throws IOException {
        FileRepresentation fileRepresentation = store.cache(compositionInstruction.dvl().url());
        InputStreamReader reader = new InputStreamReader(fileRepresentation.localStorage().openStream());
        CompositionSerializedJson comp = parseJsonStringToTrack(reader);
        for (PartJson part : comp.parts) {
            String name = part.dvlref;
            if(comp.dvls.containsKey(part.dvlref)) {
                URL url = comp.dvls.get(part.dvlref);
                part.dvl = new DvlXML(name, url);
            }
            else
                log.error("No DVL with name {} was found when parsing composition {}", part.dvlref, compositionInstruction.id());
        }
        return comp.asComposition(fileRepresentation, parser);
    }

    static CompositionSerializedJson parseJsonStringToTrack(Reader reader) {
        return jsonParser.fromJson(reader, CompositionSerializedJson.class);
    }

    static String convertCompositionToJson(CompositionXml composition) {
        return jsonParser.toJson(composition);
    }
}