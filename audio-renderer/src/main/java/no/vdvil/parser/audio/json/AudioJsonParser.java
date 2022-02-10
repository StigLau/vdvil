package no.vdvil.parser.audio.json;

import com.google.gson.Gson;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.handler.persistence.MultimediaReference;
import no.vdvil.renderer.audio.AudioDescription;
import no.vdvil.renderer.audio.Segment;
import no.vdvil.renderer.audio.Track;
import java.io.*;

public class AudioJsonParser implements MultimediaParser {

    final Store store = Store.get();
    final Gson jsonParser = new Gson();

    public MultimediaPart parse(CompositionInstruction compositionInstruction) throws IOException {
        MultimediaReference dvl = compositionInstruction.dvl();
        FileRepresentation fileRepresentation = store.cache(dvl.url(), dvl.fileChecksum());
        Track track = parseJsonStringToTrack(new InputStreamReader(new FileInputStream(fileRepresentation.localStorage())));
        track.fileRepresentation = store.createKey(track.mediaFile.fileName, track.mediaFile.checksum);
        Segment segment = track.findSegment(compositionInstruction.id());
        return new AudioDescription(segment, compositionInstruction, track);
    }

    Track parseJsonStringToTrack(Reader reader) {
        return jsonParser.fromJson(reader, Track.class);
    }
}
