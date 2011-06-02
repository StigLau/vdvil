package no.bouvet.kpro.renderer;

/**
 * The abstract Instruction class represents a rendering instruction. It must be
 * extended to provide a specific instruction that will be understood by a
 * particular AbstractRenderer implementation.
 * 
 * @author Michael Stokes
 */
public abstract class Instruction implements Comparable {
	protected int _start = 0;
	protected int _end = 0;

	/**
	 * Construct a new Instruction.
	 * 
	 * @author Michael Stokes
	 */
	public Instruction() {
	}

	/**
	 * Get the start time of this Instruction.
	 * 
	 * @return the start time in samples
	 * @author Michael Stokes
	 */
	public int getStart() {
		return _start;
	}

	/**
	 * Get the end time of this Instruction. The end time may be equal to the
	 * start time if this instruction does not have a duration
	 * 
	 * @return the end time in samples
	 * @author Michael Stokes
	 */
	public int getEnd() {
		return _end;
	}

    public int compareTo(Object o) {
        if (o instanceof Instruction) {
            Instruction other = (Instruction) o;
            if (other.getStart() < this.getStart())
                return 1;
            else if(other._start == this.getStart() && other.getEnd() < this.getEnd())
                return 1;
            else if (other.getStart() > this.getStart())
                return -1;
        }
        return 0;
    }
}
