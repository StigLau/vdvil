package no.lau.vdvil.handler;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Instructions;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Composition implements MultimediaPart {
    public final String name;
    public Float masterBpm;
    public final List<MultimediaPart> multimediaParts;
    public final URL url;

    public Composition(String name, Float masterBpm, List<MultimediaPart> multimediaParts, URL url) {
        this.name = name;
        this.masterBpm = masterBpm;
        this.multimediaParts = multimediaParts;
        this.url = url;
    }

    public Instructions instructions(Float masterBpm) throws IOException {
        Instructions instructions = new Instructions();
        for (MultimediaPart multimediaPart : multimediaParts) {
            instructions.append(multimediaPart.asInstruction(masterBpm));
        }
        return instructions;
    }

    public Instruction asInstruction(Float masterBpm) {
        throw new RuntimeException("Composition should probably not have instructions, or Instruction should be written in a different way");
    }

    public CompositionInstruction compositionInstruction() {
        throw new RuntimeException("No CompositionInstruction set up for a Composition");
    }
}

