package no.vdvil.renderer.lyric;

import no.bouvet.kpro.renderer.Instruction;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import java.io.IOException;

public class LyricDescription implements MultimediaPart {

    private String text;
    private CompositionInstruction compositionInstruction;

    public LyricDescription(String text, CompositionInstruction compositionInstruction) {

        this.text = text;
        this.compositionInstruction = compositionInstruction;
    }

    public Instruction asInstruction(Float masterBpm) {
        return LyricInstruction.create(compositionInstruction.start(), compositionInstruction().end(), masterBpm, text);
    }

    public no.lau.vdvil.instruction.Instruction asV2Instruction() {
        throw new RuntimeException("Not implemented yet");
    }

    public CompositionInstruction compositionInstruction() {
        return compositionInstruction;
    }

    public void cache(DownloadAndParseFacade downloader) throws IOException {
        //No need for caching text
    }
}
