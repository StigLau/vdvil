package no.lau.tagger.model;

import java.util.List;

/**
 * A Composition is the VaudevilleComposition that is to be played.
 * It contains a set of parts which can stem from multiple music file parts.
 * The masterBPM is used to tell what all the different parts speed should be played at.
 */
public class Composition {
    public final List<? extends AbstractPart> parts;

    public final Float masterBpm;

    public Composition(Float masterBpm, List<? extends AbstractPart> parts) {
        this.masterBpm = masterBpm;
        this.parts = parts;
    }
}