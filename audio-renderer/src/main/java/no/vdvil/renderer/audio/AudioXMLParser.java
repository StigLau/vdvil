package no.vdvil.renderer.audio;

import com.thoughtworks.xstream.XStream;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import java.io.IOException;

public class AudioXMLParser implements MultimediaParser {

    Store store = Store.get();
    XStream xstream;

    public AudioXMLParser() {
        xstream = new XStream();
        xstream.alias("track", Track.class);
        xstream.alias("mediaFile", MediaFile.class);
        xstream.alias("segment", Segment.class);
    }

    public AudioDescription parse(CompositionInstruction compositionInstruction) throws IOException {
        FileRepresentation fileRepresentation = store.cache(compositionInstruction.dvl().url());
        Track track = (Track) xstream.fromXML(fileRepresentation.localStorage());
        track.fileRepresentation = store.createKey(track.mediaFile.fileName);
        Segment segment = track.findSegment(compositionInstruction.id());
        return new AudioDescription(segment, compositionInstruction, track);
    }
}

