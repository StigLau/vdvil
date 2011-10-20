package no.bouvet.kpro.renderer;

/**
 * The abstract Instruction class represents a rendering instruction. It must be
 * extended to provide a specific instruction that will be understood by a
 * particular AbstractRenderer implementation.
 * 
 * @author Michael Stokes
 * @author Stig Lau
 */
public class Instruction implements Comparable {
	public final int _start;
	public final int _end;

	public Instruction(int start, int end) {
        this._start = start;
        this._end = end;
	}

    public int compareTo(Object o) {
        if (o instanceof Instruction) {
            Instruction other = (Instruction) o;
            if (other._start < this._start)
                return 1;
            else if (other._start > this._start)
                return -1;
            else {
                if(other._end < this._end)
                    return 1;
                else if(other._end > this._end)
                    return -1;
                }
            }
        return 0;
    }
}
