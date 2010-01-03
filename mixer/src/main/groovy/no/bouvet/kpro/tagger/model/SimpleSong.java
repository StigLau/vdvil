package no.bouvet.kpro.tagger.model;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class SimpleSong implements IPart, Serializable {
    static final long serialVersionUID = 2536680634054831206L;
    //Reference is an ID tag to the Track
    public String reference = "No reference entered";
    public MediaFile mediaFile = new MediaFile("", -1F);
    public List<Segment> segments = new ArrayList<Segment>();
    public Float bpm = 0F;

    public List<? extends IPart> children() {
        return segments;
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
