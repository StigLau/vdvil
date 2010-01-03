package no.lau.tagger.model;

public class MediaFile {
    public final String fileName;
    public final Float startingOffset;

    public MediaFile(String fileName, Float startingOffset) {
        this.fileName = fileName;
        this.startingOffset = startingOffset;
    }
}
