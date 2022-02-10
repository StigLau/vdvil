package no.vdvil.renderer.lyric;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.instruction.LyricInstruction;

public class LyricDescription implements MultimediaPart {

    private final String text;
    private final CompositionInstruction compositionInstruction;

    public LyricDescription(String text, CompositionInstruction compositionInstruction) {
        this.text = text;
        this.compositionInstruction = compositionInstruction;
    }

    public Instruction asInstruction(Float masterBpm) {
        return LyricInstruction.create(compositionInstruction.start(), compositionInstruction().end(), masterBpm, text);
    }

    public Instruction asV2Instruction() {
        return new LyricInstruction(compositionInstruction.start(),  compositionInstruction.end() - compositionInstruction.start(), text);
    }

    public CompositionInstruction compositionInstruction() {
        return compositionInstruction;
    }

    public FileRepresentation fileRepresentation() {
        return FileRepresentation.NULL;
    }
}
