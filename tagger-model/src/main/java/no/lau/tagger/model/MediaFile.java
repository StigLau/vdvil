package no.lau.tagger.model;

public class MediaFile {
    public final String fileName;
    public final Float startingOffset;
    public final String checksum;

    public MediaFile(String fileName, String checksum, Float startingOffset) {
        this.fileName = fileName;
        this.checksum = checksum;
        this.startingOffset = startingOffset;
    }
}
