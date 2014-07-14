package no.vdvil.parser.audio.json;

import com.google.gson.Gson;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.vdvil.renderer.audio.AudioDescription;
import no.vdvil.renderer.audio.Segment;
import no.vdvil.renderer.audio.Track;
import java.io.*;

public class AudioJsonParser implements MultimediaParser {

    final Store store = Store.get();
    Gson jsonParser = new Gson();

    public MultimediaPart parse(CompositionInstruction compositionInstruction) throws IOException {
        FileRepresentation fileRepresentation = store.cache(compositionInstruction.dvl().url());
        Track track = parseJsonStringToTrack(fileRepresentation);
        track.fileRepresentation = store.createKey(track.mediaFile.fileName);
        Segment segment = track.findSegment(compositionInstruction.id());
        return new AudioDescription(segment, compositionInstruction, track);
    }

    Track parseJsonStringToTrack(FileRepresentation fileRepresentation) throws FileNotFoundException {
        InputStreamReader reader = new InputStreamReader(new FileInputStream(fileRepresentation.localStorage()));
        return jsonParser.fromJson(reader, Track.class);
    }
}
