package no.lau.vdvil.handler;

import no.bouvet.kpro.renderer.AbstractInstruction;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.instruction.Instruction;

import java.io.IOException;

public interface MultimediaPart {
    MultimediaPart NULL = new MultimediaPart() {

        public AbstractInstruction asInstruction(Float masterBpm) {
            return null;
        }

        @Override
        public no.lau.vdvil.instruction.Instruction asV2Instruction() {
            return null;
        }

        public CompositionInstruction compositionInstruction() {
            return null;
        }

        public void cache(DownloadAndParseFacade downloader) throws IOException { }
    };

    Instruction asInstruction(Float masterBpm);

    Instruction asV2Instruction();

    CompositionInstruction compositionInstruction();

    /**
     * Try to download multimedia payload to cache
     */
    void cache(DownloadAndParseFacade downloader) throws IOException;
}
