package no.lau.vdvil.instruction;

/**
 * Superclass for containing all the dull stuff of a Instruction
 */
public abstract class SuperInstruction implements Instruction {
    final long start;
    final long length;

    public SuperInstruction(long start, long length) {
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

}
