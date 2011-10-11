package no.bouvet.kpro.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The Instructions class is a collection of renderer Instructions. It supports
 * emptying the list, appending new instructions, determining the duration of
 * the instruction list, and exclusive locking for use by the renderer.
 * 
 * @author Michael Stokes
 */
public class Instructions {
	protected ArrayList<Instruction> _list = new ArrayList<Instruction>();
	protected int _duration = 0;
	protected boolean _locked = false;

	/**
	 * Construct a new Instructions list
	 * 
	 * @author Michael Stokes
	 */
	public Instructions() {
	}

	/**
	 * Lock the Instructions list, returning an ArrayList that can be accessed
	 * exclusively until unlock() is called.
	 * 
	 * @return an ArrayList<Instruction> of instructions, or null if already
	 *         locked
	 * @author Michael Stokes
	 */
	public synchronized ArrayList<Instruction> lock() {
		if (_locked) {
			return null;
		} else {
			_locked = true;
			return _list;
		}
	}

	/**
	 * Unlock the Instructions list, so that it can be modified again.
	 * 
	 * @author Michael Stokes
	 */
	public synchronized void unlock() {
		_locked = false;
	}

	/**
	 * Empty the Instructions list, if it is not locked.
	 */
	public synchronized void empty() {
		if (!_locked) {
			_list.clear();
			_duration = 0;
		}
	}

	/**
	 * Append a new Instruction to the Instructions list, if it is not locked.
	 * 
	 * @param instruction
	 *            The instruction to append
	 * @author Michael Stokes
	 */
	public synchronized void append(Instruction instruction) {
		if (!_locked) {
			_list.add(instruction);
			if (instruction.getEnd() > _duration)
				_duration = instruction.getEnd();
            Collections.sort(_list);
		}
	}

	/**
	 * Get the duration of the Instructions list.
	 * 
	 * @return the duration in samples
	 * @author Michael Stokes
	 */
	public synchronized int getDuration() {
		return _duration;
	}

    public void endAt(int end) {
        append(new StopInstruction(end));
    }

    public List<Instruction> sortedByEnd() {
    List<Instruction> instructionList = new ArrayList<Instruction>(_list);
        Collections.sort(instructionList, new EndSorter());
        return instructionList;
    }
}

class EndSorter implements Comparator {
  public int compare(Object i1, Object i2) {
    return ((Integer)((Instruction)i1).getEnd()).compareTo(((Instruction)i2).getEnd());
  }
}
