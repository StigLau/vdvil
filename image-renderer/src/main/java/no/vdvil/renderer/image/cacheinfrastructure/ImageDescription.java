package no.vdvil.renderer.image.cacheinfrastructure;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.instruction.ImageInstruction;
import no.lau.vdvil.timing.MasterBeatPattern;

public class ImageDescription implements MultimediaPart{

    public final CompositionInstruction compositionInstruction;
    private FileRepresentation fileRepresentation;

    public ImageDescription(CompositionInstruction compositionInstruction, FileRepresentation fileRepresentation) {
        this.compositionInstruction = compositionInstruction;
        this.fileRepresentation = fileRepresentation;
    }

    public ImageInstruction asInstruction(Float masterBpm) {
        return ImageInstruction.create(new MasterBeatPattern(compositionInstruction.timeInterval(), masterBpm), fileRepresentation);
    }

    public no.lau.vdvil.instruction.ImageInstruction asV2Instruction() {
        return new ImageInstruction(compositionInstruction.start(),  compositionInstruction.end() - compositionInstruction.start(), fileRepresentation);
    }

    public CompositionInstruction compositionInstruction() {
        return compositionInstruction;
    }

    public FileRepresentation fileRepresentation() {
        return fileRepresentation;
    }

    public void updateFileRepresentation(FileRepresentation fileRepresentation) {
        this.fileRepresentation = fileRepresentation;
    }
}
