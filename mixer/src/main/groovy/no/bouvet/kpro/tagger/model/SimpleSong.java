package no.bouvet.kpro.tagger.model;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class SimpleSong implements IPart, Serializable {
    static final long serialVersionUID = 2536680634054831206L;

    public String fileName = "";
    public List<IPart> rows = new ArrayList<IPart>();
    public Float bpm = 0F;
    public Float startingOffset = 0F;

    public List<IPart> children() {
        return rows;
    }
}
