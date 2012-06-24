package no.lau.vdvil.renderer;

import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.instruction.SortInstructionHelper;

public class ImageInstruction implements Instruction {
    final String imageUrl;
    private final String meta;
    private final long start;
    private final long length;

    public ImageInstruction(String imageUrl, String meta, int start, int length) {
        this.imageUrl = imageUrl;
        this.meta = meta;
        this.start = start;
        this.length = length;
    }

    public long start() {
        return start;
    }

    public long length() {
        return length;
    }

    public long end() {
        return start + length;
    }

    public int compareTo(Object other) {
        return SortInstructionHelper.compareTo(this, other);
    }

}

