package no.bouvet.kpro.tagger;

import no.bouvet.kpro.tagger.model.Part;
import no.bouvet.kpro.tagger.model.Row;
import no.bouvet.kpro.tagger.model.SimpleSong;

public class PartCreationUtil {
    public static Part createPart(SimpleSong ss, Float start, Float end, Row row) {
        Part part = new Part();
        part.simpleSong = ss;
        part.bpm = ss.bpm;
        part.startCue = start;
        part.endCue = end;
        part.row = row;
        return part;
    }
}
