package no.vdvil.renderer.audio;

import java.util.List;

public class Track {
    public final String reference;
    public final Float bpm;
    public final MediaFile mediaFile;
    public final List<Segment> segments;

    public Track(String reference, Float bpm, MediaFile mediaFile, List<Segment> segments) {
        this.reference = reference;
        this.bpm = bpm;
        this.mediaFile = mediaFile;
        this.segments = segments;
    }

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
