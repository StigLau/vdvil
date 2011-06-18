package no.lau.vdvil.handler;

import no.bouvet.kpro.renderer.Instruction;
import no.lau.vdvil.handler.persistence.CompositionInstruction;

import java.io.IOException;

public interface MultimediaPart {
    MultimediaPart NULL = new MultimediaPart() {

        public Instruction asInstruction(Float masterBpm) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public CompositionInstruction compositionInstruction() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void cache(DownloadAndParseFacade downloader) throws IOException { }
    };

    Instruction asInstruction(Float masterBpm);

    CompositionInstruction compositionInstruction();

    /**
     * Try to download multimedia payload to cache
     * @param downloader
     * @throws IOException
     */
    void cache(DownloadAndParseFacade downloader) throws IOException;
}
