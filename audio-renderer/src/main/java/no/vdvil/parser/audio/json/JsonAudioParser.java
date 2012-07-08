package no.vdvil.parser.audio.json;

import com.google.gson.Gson;
import no.lau.vdvil.cache.DownloaderFacade;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.vdvil.renderer.audio.AudioDescription;
import no.vdvil.renderer.audio.Segment;
import no.vdvil.renderer.audio.Track;

import java.io.*;
import java.net.URL;

public class JsonAudioParser implements MultimediaParser {

    DownloaderFacade downloader;
    Gson jsonParser;

    public JsonAudioParser(DownloaderFacade downloader) {
        this.downloader = downloader;
        this.jsonParser = new Gson();
    }

    @Override
    public MultimediaPart parse(CompositionInstruction compositionInstruction) throws IOException {
        URL dvlUrl = compositionInstruction.dvl().url();
        InputStreamReader reader = new InputStreamReader(downloader.fetchAsStream(dvlUrl));

        Track track = parseJsonStringToTrack(reader);
        Segment segment = track.findSegment(compositionInstruction.id());
        return new AudioDescription(segment, compositionInstruction, track);
    }

    Track parseJsonStringToTrack(Reader reader) {
        return jsonParser.fromJson(reader, Track.class);
    }
}
