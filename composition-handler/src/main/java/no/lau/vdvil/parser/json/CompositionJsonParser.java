package no.lau.vdvil.parser.json;

import com.google.gson.Gson;
import no.lau.vdvil.cache.DownloaderFacade;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.handler.persistence.domain.CompositionXml;

import java.io.*;
import java.net.URL;

public class CompositionJsonParser implements MultimediaParser {

    DownloaderFacade downloader;
    Gson jsonParser;

    public CompositionJsonParser(DownloaderFacade downloader) {
        this.downloader = downloader;
        this.jsonParser = new Gson();
    }

    @Override
    public MultimediaPart parse(CompositionInstruction compositionInstruction) throws IOException {
        URL dvlUrl = compositionInstruction.dvl().url();
        InputStreamReader reader = new InputStreamReader(downloader.fetchAsStream(dvlUrl));
/*
        CompositionXml compositionXml = parseJsonStringToTrack(reader);
        Segment segment = compositionXml.findSegment(compositionInstruction.id());
        return new AudioDescription(segment, compositionInstruction, track);
        */
        return null;
    }

    CompositionXml parseJsonStringToTrack(Reader reader) {
        return jsonParser.fromJson(reader, CompositionXml.class);
    }
}
