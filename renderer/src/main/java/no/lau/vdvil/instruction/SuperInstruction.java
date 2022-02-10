package no.lau.vdvil.instruction;

import no.lau.vdvil.cache.FileRepresentation;

/**
 * Superclass for containing all the dull stuff of a Instruction
 */
public abstract class SuperInstruction implements Instruction {
    final long start;
    final long length;

    //Used for printing out instructions original BPM
    public int startAsBpm;
    public int durationAsBpm;

    protected final FileRepresentation fileRepresentation;

    public SuperInstruction(long start, long length, FileRepresentation fileRepresentation) {
        this.start = start;
        this.length = length;
        this.fileRepresentation = fileRepresentation;
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

    public FileRepresentation fileRepresentation(){
        return fileRepresentation;
    }

    public int compareTo(Object otherO) {
        Instruction a = (Instruction) otherO;
        Instruction b = this;
        if (a.start() < b.start())
            return 1;
        else if (a.start() > b.start())
            return -1;
        else {
            if (a.end() < b.end())
                return 1;
            else if (a.end() > b.end())
                return -1;
        }
        return 0;
    }

    public String toString() {
        return startAsBpm + " + " + durationAsBpm;
    }
}
