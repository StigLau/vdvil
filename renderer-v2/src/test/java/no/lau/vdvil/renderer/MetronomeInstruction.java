package no.lau.vdvil.renderer;

public class MetronomeInstruction implements Instruction{

    final long start;
    final long length;

    public MetronomeInstruction(long start, long length) {
        this.start = start;
        this.length = length;
    }

    public long start() {
        return start;
    }

    public long length() {
        return length;
    }
}
