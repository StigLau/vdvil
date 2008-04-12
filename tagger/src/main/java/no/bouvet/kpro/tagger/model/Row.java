package no.bouvet.kpro.tagger.model;

import java.io.Serializable;

public class Row implements Serializable {
    String text;
    Float cue;
    Float end;

    Row(Float cue, Float end, String text) {
        this.cue = cue;
        this.end = end;
        this.text = text;
    }
}


