package no.bouvet.kpro.tagger.model;

import java.util.List;
import java.util.ArrayList;

public class MasterSong implements IPart {

    public String label;

    public List<IPart> parts = new ArrayList<IPart>();

    public Float masterBpm;

    public List<IPart> children() {
        return parts;
    }
}