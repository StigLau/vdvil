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
        xstream = new XStream();
        xstream.alias("track", Track.class);
        xstream.alias("mediaFile", MediaFile.class);
        xstream.alias("segment", Segment.class);
    }

    public AudioDescription parse(URL dvlUrl) throws IOException {
        Track track = (Track) xstream.fromXML(downloaderAndParseFacade.fetchAsStream(dvlUrl));

        URL fileInCache = downloaderAndParseFacade.fetchFromCache(track.mediaFile.fileName, track.mediaFile.checksum);
        return new AudioDescription(track, dvlUrl, fileInCache);
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
}

class MediaFile {
    URL fileName;
    Float startingOffset;
    String checksum;
}

class Segment {
    String id;
    String text;
    int start;
    int end;
}
