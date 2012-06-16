package no.lau.vdvil.renderer;

public class ImageInstruction implements Instruction{
    private final String imageUrl;
    private final String meta;
    private final int start;
    private final int length;

    public ImageInstruction(String imageUrl, String meta, int start, int length) {
        this.imageUrl = imageUrl;
        this.meta = meta;
        this.start = start;
        this.length = length;
    }

    @Override
    public int getStartBeat() {
        return start;
    }
}

