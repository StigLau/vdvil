package no.lau.vdvil.handler;

import no.lau.vdvil.handler.persistence.CompositionInstruction;

import java.io.IOException;

public interface MultimediaPart {
    MultimediaPart NULL = new MultimediaPart() {

        public no.bouvet.kpro.renderer.Instruction asInstruction(Float masterBpm) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public no.lau.vdvil.instruction.Instruction asV2Instruction() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public CompositionInstruction compositionInstruction() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void cache(DownloadAndParseFacade downloader) throws IOException { }
    };

    no.bouvet.kpro.renderer.Instruction asInstruction(Float masterBpm);

    no.lau.vdvil.instruction.Instruction asV2Instruction();

    CompositionInstruction compositionInstruction();

    /**
     * Try to download multimedia payload to cache
     * @param downloader
     * @throws IOException
     */
    void cache(DownloadAndParseFacade downloader) throws IOException;
}
