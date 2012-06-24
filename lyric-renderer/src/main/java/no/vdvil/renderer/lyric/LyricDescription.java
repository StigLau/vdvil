package no.vdvil.renderer.lyric;

import no.bouvet.kpro.renderer.AbstractInstruction;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.instruction.LyricInstruction;
import java.io.IOException;

public class LyricDescription implements MultimediaPart {

    private String text;
    private CompositionInstruction compositionInstruction;

    public LyricDescription(String text, CompositionInstruction compositionInstruction) {

        this.text = text;
        this.compositionInstruction = compositionInstruction;
    }

    public AbstractInstruction asInstruction(Float masterBpm) {
        return LyricInstruction.create(compositionInstruction.start(), compositionInstruction().end(), masterBpm, text);
    }

    public no.lau.vdvil.instruction.Instruction asV2Instruction() {
        return new LyricInstruction(compositionInstruction.start(),  compositionInstruction.end() - compositionInstruction.start(), text);
    }

    public CompositionInstruction compositionInstruction() {
        return compositionInstruction;
    }

    public void cache(DownloadAndParseFacade downloader) throws IOException {
        //No need for caching text
    }
}
