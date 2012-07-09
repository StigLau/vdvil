package no.lau.vdvil.parser.json;

import com.google.gson.Gson;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.handler.persistence.domain.CompositionXml;
import java.io.*;
import java.net.URL;

public class CompositionJsonParser implements MultimediaParser {

    DownloadAndParseFacade downloader;
    Gson jsonParser;

    public CompositionJsonParser(DownloadAndParseFacade downloader) {
        this.downloader = downloader;
        this.jsonParser = new Gson();
    }

    public MultimediaPart parse(CompositionInstruction compositionInstruction) throws IOException {
        URL dvlUrl = compositionInstruction.dvl().url();
        InputStreamReader reader = new InputStreamReader(downloader.fetchAsStream(dvlUrl));
        CompositionXml compositionXml = parseJsonStringToTrack(reader);
        return compositionXml.asComposition(dvlUrl, downloader);
    }

    CompositionXml parseJsonStringToTrack(Reader reader) {
        return jsonParser.fromJson(reader, CompositionXml.class);
    }

    String convertCompositionToJson(CompositionXml composition){
        return jsonParser.toJson(composition);
    }
}
