package no.lau.tagger.model;

import java.util.List;
import java.util.Collections;

public class Part implements IPart {
    public final SimpleSong simpleSong;
    public final Segment segment;

    public final Float bpm;
    public final Float startCue;
    public final Float endCue;

    public final Float beginAtCue;

    /**
     * Simple constructor for most usages
     */
    public Part(SimpleSong simpleSong, Float startCue, Float endCue, Segment segment) {
        this.simpleSong = simpleSong;
        this.segment = segment;
        this.bpm = simpleSong.bpm;
        this.startCue = startCue;
        this.endCue = endCue;
        this.beginAtCue = 0F;
    }

    public Part(SimpleSong simpleSong, Segment segment, Float bpm, Float startCue, Float endCue, Float beginAtCue) {
        this.simpleSong = simpleSong;
        this.segment = segment;
        this.bpm = bpm;
        this.startCue = startCue;
        this.endCue = endCue;
        this.beginAtCue = beginAtCue;
    }

    public List<IPart> children() {
        return Collections.emptyList();
    }
}