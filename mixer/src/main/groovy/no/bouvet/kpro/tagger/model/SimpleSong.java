package no.bouvet.kpro.tagger.model;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class SimpleSong implements IPart, Serializable {
    static final long serialVersionUID = 2536680634054831206L;

    public String fileName = "";
    public List<Row> rows = new ArrayList<Row>();
    public Float bpm = 0F;
    public Float startingOffset = 0F;

    public List<? extends IPart> children() {
        return rows;
    }
}
