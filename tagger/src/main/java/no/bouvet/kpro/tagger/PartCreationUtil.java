package no.bouvet.kpro.tagger;

import no.bouvet.kpro.tagger.model.Part;
import no.bouvet.kpro.tagger.model.Segment;
import no.bouvet.kpro.tagger.model.SimpleSong;

public class PartCreationUtil {
    public static Part createPart(SimpleSong ss, Float start, Float end, Segment segment) {
        Part part = new Part();
        part.simpleSong = ss;
        part.bpm = ss.bpm;
        part.startCue = start;
        part.endCue = end;
        part.segment = segment;
        return part;
    }
}
