package no.bouvet.kpro.tagger.model;

import java.util.List;
import java.util.ArrayList;

@Deprecated //What is MAsterSong used for?
public class MasterSong implements IPart {

    //public String label;

    public final List<Part> parts;

    public final Float masterBpm;

    public MasterSong(Float masterBpm, List<Part> parts) {
        this.masterBpm = masterBpm;
        this.parts = parts;
    }

    public List<? extends IPart> children() {
        return parts;
    }
}