package no.bouvet.kpro.tagger.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

//TODO Fields should be final!
public class Segment implements IPart, Serializable {
    public String id;
    public String text;
    public Float start;
    public Float end;

    public Segment(String id, Float start, Float end, String text) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.text = text;
    }

    public List<IPart> children() {
        return Collections.emptyList();
    }
}


