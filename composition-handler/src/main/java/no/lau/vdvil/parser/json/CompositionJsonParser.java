package no.lau.vdvil.parser.json;

import com.google.gson.Gson;
import no.lau.vdvil.handler.DownloadAndParseFacade;
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

    DownloadAndParseFacade downloader;
    Gson jsonParser;
    Logger log = LoggerFactory.getLogger(getClass());

    public CompositionJsonParser(DownloadAndParseFacade downloader) {
        this.downloader = downloader;
        this.jsonParser = new Gson();
    }

    public MultimediaPart parse(CompositionInstruction compositionInstruction) throws IOException {
        URL dvlUrl = compositionInstruction.dvl().url();
        InputStreamReader reader = new InputStreamReader(downloader.fetchAsStream(dvlUrl));
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
        return comp.asComposition(dvlUrl, downloader);
    }

    CompositionSerializedJson parseJsonStringToTrack(Reader reader) {
        return jsonParser.fromJson(reader, CompositionSerializedJson.class);
    }

    String convertCompositionToJson(CompositionXml composition) {
        return jsonParser.toJson(composition);
    }
}