package no.lau.tagger.model;

import java.io.Serializable;

public class Segment implements Serializable {
    public final String id;
    public final String text;
    public final Float start;
    public final Float end;

    public Segment(String id, Float start, Float end, String text) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.text = text;
    }
}


