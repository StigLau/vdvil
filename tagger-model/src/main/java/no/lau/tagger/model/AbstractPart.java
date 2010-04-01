package no.lau.tagger.model;

import no.bouvet.kpro.renderer.Instruction;
import java.io.IOException;

public abstract class AbstractPart implements IPart {
    public final Float startCue;
    public final Float endCue;

    public AbstractPart(Float startCue, Float endCue) {
        this.startCue = startCue;
        this.endCue = endCue;
    }

    public abstract Instruction translateToInstruction(Float masterBpm) throws IOException;
}
