package no.vdvil.renderer.audio;

import java.net.URL;

public class MediaFile {
    public final URL fileName;
    public final Float startingOffset;
    public final String checksum;

    public MediaFile(URL url, Float startingOffsetInMillis, String checksum) {
        this.fileName = url;
        this.startingOffset = startingOffsetInMillis;
        this.checksum = checksum;
    }
}
