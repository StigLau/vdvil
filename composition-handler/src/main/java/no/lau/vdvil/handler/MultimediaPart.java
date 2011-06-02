package no.lau.vdvil.handler;

import no.bouvet.kpro.renderer.Instruction;

import java.io.IOException;

public interface MultimediaPart {
    MultimediaPart NULL = new MultimediaPart() {

        public Instruction asInstruction(Float masterBpm) throws IOException {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    };

    Instruction asInstruction(Float masterBpm) throws IOException;
}
