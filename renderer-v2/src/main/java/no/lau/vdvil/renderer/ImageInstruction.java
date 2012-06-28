package no.lau.vdvil.renderer;

import no.lau.vdvil.instruction.SuperInstruction;

public class ImageInstruction extends SuperInstruction{
    final String imageUrl;
    private final String meta;

    public ImageInstruction(String imageUrl, String meta, int start, int length) {
        super(start, length);
        this.imageUrl = imageUrl;
        this.meta = meta;
    }
}