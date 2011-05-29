package no.vdvil.renderer.audio;

import com.thoughtworks.xstream.XStream;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaParser;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class AudioXMLParser implements MultimediaParser {

    DownloadAndParseFacade downloaderAndParseFacade;
    XStream xstream;

    public AudioXMLParser() {
        this(null);
    }
    public AudioXMLParser(DownloadAndParseFacade downloaderAndParseFacade) {
        this.downloaderAndParseFacade = downloaderAndParseFacade;
        xstream = new XStream();
        xstream.alias("track", Track.class);
        xstream.alias("mediaFile", MediaFile.class);
        xstream.alias("segment", Segment.class);
    }

    public AudioDescription parse(String id, URL dvlUrl) throws IOException {
        Track track = (Track) xstream.fromXML(downloaderAndParseFacade.fetchAsStream(dvlUrl));
        Segment segment = track.findSegment(id);
        URL fileInCache = downloaderAndParseFacade.fetchFromCache(track.mediaFile.fileName, track.mediaFile.checksum);
        return new AudioDescription(segment, track, dvlUrl, fileInCache);
    }

    public void setDownloaderAndParseFacade(DownloadAndParseFacade downloaderAndParseFacade) {
        this.downloaderAndParseFacade = downloaderAndParseFacade;
    }
}

class Track {
    String reference;
    Float bpm;
    MediaFile mediaFile;
    List<Segment> segments;

    /**
     * @param id Searches for a Segment by its id
     * @return Segment.NULL if no segment found with this ID
     */
    public Segment findSegment(String id) {
        for (Segment segment : segments) {
            if(segment.id.equals(id))
                return segment;
        }
        return Segment.NULL;
    }
}

class MediaFile {
    URL fileName;
    Float startingOffset;
    String checksum;
}

class Segment {
    public static final Segment NULL = new Segment();
    String id;
    String text;
    int start;
    int end;
}
