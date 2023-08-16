package no.vdvil.renderer.audio;

import java.net.URI;

public class MediaFile {
    public final URI fileName;
    public final Float startingOffset;
    public final String checksum;

    public MediaFile(URI fileRef, Float startingOffsetInMillis, String checksum) {
        this.fileName = fileRef;
        this.startingOffset = startingOffsetInMillis;
        this.checksum = checksum;
    }
}
