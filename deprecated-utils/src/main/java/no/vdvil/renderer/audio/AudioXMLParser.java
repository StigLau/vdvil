package no.vdvil.renderer.audio;

import com.thoughtworks.xstream.XStream;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.handler.persistence.MultimediaReference;
import java.io.IOException;

public class AudioXMLParser implements MultimediaParser {

    final Store store = Store.get();
    final XStream xstream;

    public AudioXMLParser() {
        xstream = new XStream();
        xstream.alias("track", Track.class);
        xstream.alias("mediaFile", MediaFile.class);
        xstream.alias("segment", Segment.class);
        xstream.allowTypesByWildcard(new String[] {
                "no.vdvil.renderer.audio.Track",
                "no.vdvil.renderer.audio.MediaFile",
                "no.vdvil.renderer.audio.Segment"
        });
    }

    public AudioDescription parse(CompositionInstruction compositionInstruction) throws IOException {
        MultimediaReference dvl = compositionInstruction.dvl();
        FileRepresentation fileRepresentation = store.cache(dvl.url(), dvl.fileChecksum());
        Track track = (Track) xstream.fromXML(fileRepresentation.localStorage());
        track.fileRepresentation = store.createKey(track.mediaFile.fileName.toURL(), track.mediaFile.checksum);
        Segment segment = track.findSegment(compositionInstruction.id());
        return new AudioDescription(segment, compositionInstruction, track);
    }
}

