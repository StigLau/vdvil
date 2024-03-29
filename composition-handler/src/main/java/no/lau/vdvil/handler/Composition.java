package no.lau.vdvil.handler;

import no.bouvet.kpro.renderer.Instructions;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.timing.MasterBeatPattern;
import java.util.List;

public class Composition implements MultimediaPart {
    public final String name;
    public final MasterBeatPattern masterBeatPattern;
    public final List<MultimediaPart> multimediaParts;
    public FileRepresentation fileRepresentation;

    public Composition(String name, MasterBeatPattern masterBeatPattern, FileRepresentation fileRepresentation, Compositeur compositeur) {
        this.name = name;
        this.masterBeatPattern = masterBeatPattern;
        this.multimediaParts = compositeur.parts();
        this.fileRepresentation = fileRepresentation;
    }

    /**
     * Converts composition to Instructions.
     * @param movement If the composition is to start on a differnent BPM than 0. null if movement isn't necessary
     */
    public Instructions instructions(Float masterBpm, Integer movement) {
        Instructions instructions = new Instructions();
        for (MultimediaPart multimediaPart : multimediaParts) {
            if (movement != null && multimediaPart instanceof MutableInstruction) {
                MutableInstruction mutableInstruction = (MutableInstruction) multimediaPart;
                mutableInstruction.moveStart(movement);
            }
            instructions.append(multimediaPart.asInstruction(masterBpm));
        }
        return instructions;
    }

    public Instruction asInstruction(Float masterBpm) {
        throw new RuntimeException("Composition should probably not have instructions, or AbstractInstruction should be written in a different way");
    }

    public Instruction asV2Instruction() {
        throw new RuntimeException("No CompositionInstruction set up for a Composition");
    }

    public CompositionInstruction compositionInstruction() {
        throw new RuntimeException("No CompositionInstruction set up for a Composition");
    }

    public FileRepresentation fileRepresentation() {
        return this.fileRepresentation;
    }

    /**
     * Creates a copy of this Composition with a different beatPattern
     */
    public Composition withBeatPattern(MasterBeatPattern beatPattern) {
        return new Composition(name, beatPattern, fileRepresentation, () -> multimediaParts);
    }

    public String toString() {
        return name + "; " + masterBeatPattern.toString() + ". " + multimediaParts.size() + " parts" ;
    }

    public void updateFileRepresentation(FileRepresentation fileRepresentation) {
        this.fileRepresentation = fileRepresentation;
    }
}

