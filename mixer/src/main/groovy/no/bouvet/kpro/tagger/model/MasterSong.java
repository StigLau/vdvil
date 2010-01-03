package no.bouvet.kpro.tagger.model;

import java.util.List;
import java.util.ArrayList;

@Deprecated //What is MAsterSong used for?
public class MasterSong implements IPart {

    //public String label;

    public List<Part> parts = new ArrayList<Part>();

    public Float masterBpm;

    public List<? extends IPart> children() {
        return parts;
    }
}