package no.lau.tagger.model;

import java.io.Serializable;

public class Segment implements Serializable {
    public final String id;
    public final String text;
    public final int start;
    public final int end;

    public Segment(String id, int start, int end, String text) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.text = text;
    }
}


