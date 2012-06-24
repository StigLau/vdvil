package no.bouvet.kpro.renderer;

import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.instruction.SortInstructionHelper;

/**
 * The abstract AbstractInstruction class represents a rendering instruction. It must be
 * extended to provide a specific instruction that will be understood by a
 * particular AbstractRenderer implementation.
 * 
 * @author Michael Stokes
 * @author Stig Lau
 */
public class AbstractInstruction implements Instruction {
	public final int _start;
	public final int _end;

	public AbstractInstruction(int start, int end) {
        this._start = start;
        this._end = end;
	}

    @Deprecated
    public AbstractInstruction() {
        _start = -1;
        _end = -1;
    }

    public long start() {
        return _start;
    }

    public long length() {
        return _end-_start;
    }

    public long end() {
        return _end;
    }

    public int compareTo(Object other) {
        return SortInstructionHelper.compareTo(this, other);
    }
}
