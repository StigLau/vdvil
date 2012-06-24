package no.lau.vdvil.timing;

import no.lau.vdvil.instruction.Instruction;

public class ResolutionInstruction implements Instruction {

    private final long start;
    private final long length;
    public final int speed;
    public final int perMinute;

    public ResolutionInstruction(long start, long length, int speed, int perMinute) {
        this.start = start;
        this.length = length;
        this.speed = speed;
        this.perMinute = perMinute;
    }

    public long start() {
        return start;
    }

    public long length() {
        return length;
    }
}
