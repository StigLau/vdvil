package no.bouvet.kpro.tagger.model;

import java.util.List;
import java.util.Collections;

public class Part implements IPart {
    public SimpleSong simpleSong;
    public Segment segment;

    public Float bpm;
    public Float startCue;
    public Float endCue;

    public Float beginAtCue;

    public List<IPart> children() {
        return Collections.emptyList();
    }
}