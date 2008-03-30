package no.bouvet.kpro.tagger;

public class Row {
    String text;
    Float cue;
    Float end;

    Row(Float cue, Float end, String text) {
        this.cue = cue;
        this.end = end;
        this.text = text;
    }
}


