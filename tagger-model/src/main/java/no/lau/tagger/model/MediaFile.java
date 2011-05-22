package no.lau.tagger.model;

import java.net.URL;

public class MediaFile {
    public final URL fileName;
    public final Float startingOffset;
    public final String checksum;

    public MediaFile(URL fileName, String checksum, Float startingOffset) {
        this.fileName = fileName;
        this.checksum = checksum;
        this.startingOffset = startingOffset;
    }
}
