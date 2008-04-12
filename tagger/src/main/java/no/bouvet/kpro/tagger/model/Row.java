package no.bouvet.kpro.tagger.model;

import java.io.Serializable;

public class Row implements Serializable {
    public String text;
    public Float cue;
    public Float end;

    public Row(Float cue, Float end, String text) {
        this.cue = cue;
        this.end = end;
        this.text = text;
    }
}


