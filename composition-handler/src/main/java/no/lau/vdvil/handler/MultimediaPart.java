package no.lau.vdvil.handler;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.instruction.Instruction;

public interface MultimediaPart {
    MultimediaPart NULL = new MultimediaPart() {

        public Instruction asInstruction(Float masterBpm) {
            return null;
        }

        public no.lau.vdvil.instruction.Instruction asV2Instruction() {
            return null;
        }

        public CompositionInstruction compositionInstruction() {
            return null;
        }

        public FileRepresentation fileRepresentation() {
            return FileRepresentation.NULL;
        }
    };

    Instruction asInstruction(Float masterBpm);

    Instruction asV2Instruction();

    CompositionInstruction compositionInstruction();

    public FileRepresentation fileRepresentation();
}
