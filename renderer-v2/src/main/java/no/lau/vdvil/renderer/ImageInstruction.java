package no.lau.vdvil.renderer;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.instruction.SuperInstruction;

public class ImageInstruction extends SuperInstruction{
    private final String meta;

    public ImageInstruction(String meta, int start, int length, FileRepresentation fileRepresentation) {
        super(start, length, fileRepresentation);
        this.meta = meta;
    }
}