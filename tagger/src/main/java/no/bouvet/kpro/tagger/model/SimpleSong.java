package no.bouvet.kpro.tagger.model;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class SimpleSong implements Serializable {
    static final long serialVersionUID = 2536680634054831206L; 

    public String fileName = "";
    public List<Row> rows = new ArrayList<Row>();
    public Float bpm = 0F;
    public Float startingOffset = 0F;

    public String toString() {
        String result = ("bpm = " + bpm);
        result += "\n" + "startingOffset = " + startingOffset;
        for (Row row : rows) {
            result += "\n row.cue = " + row.cue;
        }
        return result;
    }
}
