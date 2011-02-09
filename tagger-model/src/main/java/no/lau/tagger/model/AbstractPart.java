package no.lau.tagger.model;

import no.bouvet.kpro.renderer.Instruction;
import java.io.IOException;

public abstract class AbstractPart {
    public final int startCue;
    public final int endCue;

    public AbstractPart(int startCue, int endCue) {
        this.startCue = startCue;
        this.endCue = endCue;
    }

    public abstract Instruction translateToInstruction(Float masterBpm) throws IOException;
}
