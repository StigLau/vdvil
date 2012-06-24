package no.lau.vdvil.handler;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Instructions;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.timing.MasterBeatPattern;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Composition implements MultimediaPart {
    public final String name;
    public final MasterBeatPattern masterBeatPattern;
    public final List<MultimediaPart> multimediaParts;
    public final URL url;

    public Composition(String name, MasterBeatPattern masterBeatPattern, List<MultimediaPart> multimediaParts, URL url) {
        this.name = name;
        this.masterBeatPattern = masterBeatPattern;
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

    public no.lau.vdvil.instruction.Instruction asV2Instruction() {
        throw new RuntimeException("No CompositionInstruction set up for a Composition");
    }

    public CompositionInstruction compositionInstruction() {
        throw new RuntimeException("No CompositionInstruction set up for a Composition");
    }

    public void cache(DownloadAndParseFacade downloader) throws IOException {
        for (MultimediaPart multimediaPart : multimediaParts) {
            multimediaPart.cache(downloader);
        }
    }

    /**
     * Creates a copy of this Composition with a different beatPattern
     */
    public Composition withBeatPattern(MasterBeatPattern beatPattern) {
        return new Composition(name, beatPattern, multimediaParts, url);
    }

    public String toString() {
        return name + "; " + masterBeatPattern.toString() + ". " + multimediaParts.size() + " parts" ;
    }
}

