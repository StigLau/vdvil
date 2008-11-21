package no.bouvet.kpro.tagger.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Row implements IPart, Serializable {
    public String text;
    public Float cue;
    public Float end;

    public Row(Float cue, Float end, String text) {
        this.cue = cue;
        this.end = end;
        this.text = text;
    }

    public List<IPart> children() {
        return Collections.emptyList();
    }
}


