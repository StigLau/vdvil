package no.lau.tagger.model;

import java.util.List;

public class SimpleSong {
    //Reference is an ID tag to the Track
    public final String reference;
    public final MediaFile mediaFile;
    public final Float bpm;
    public final List<Segment> segments;

    public SimpleSong(String reference, MediaFile mediaFile, List<Segment> segments, Float bpm) {
        this.reference = reference;
        this.mediaFile = mediaFile;
        this.segments = segments;
        this.bpm = bpm;
    }

    public String toString() {
        String result = ("bpm = " + bpm);
        result += "\n" + "startingOffset = " + mediaFile.startingOffset;
        for (Segment segment : segments) {
            result += "\n segment.start = " + segment.start;
        }
        return result;
    }
}
