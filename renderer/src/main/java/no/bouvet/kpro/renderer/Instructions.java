package no.bouvet.kpro.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import no.lau.vdvil.instruction.Instruction;

/**
 * The Instructions class is a collection of renderer Instructions. It supports
 * emptying the list, appending new instructions, determining the duration of
 * the instruction list, and exclusive locking for use by the renderer.
 * 
 * @author Michael Stokes
 * @author Stig Lau
 */
public class Instructions {
	List<Instruction> _list = new ArrayList<Instruction>();
	long _duration = 0;
	boolean _locked = false;

	/**
	 * Lock the Instructions list, returning an List that can be accessed exclusively until unlock() is called.
	 * 
	 * @return a List<Instruction> of instructions, or null if already locked
	 */
	public synchronized List<Instruction> lock() {
		if (_locked) {
			return null;
		} else {
			_locked = true;
			return _list;
		}
	}

	/**
	 * Unlock the Instructions list, so that it can be modified again.
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
	 * Append a new AbstractInstruction to the Instructions list, if it is not locked.
	 */
	public synchronized void append(Instruction instruction) {
		if (!_locked) {
			_list.add(instruction);
			if (instruction.end() > _duration)
				_duration = instruction.end();
            Collections.sort(_list);
		}
	}

    /**
	 * Get the duration of the Instructions list.
	 * 
	 * @return the duration in samples
	 */
	public synchronized long getDuration() {
		return _duration;
	}

    /**
     * Creates a copy of the instructions list sorted by the endings.
     * Used for stopping instructions
     */
    List<Instruction> sortedByEnd() {
        List<Instruction> instructionList = instructionsCopy();
        Collections.sort(instructionList, new EndSorter());
        return instructionList;
    }

    /**
     * @return a current copy of the instructions list. Note that this function is not thread safe. If Threads are an issue, use lock/unlock!
     */
    public List<Instruction> instructionsCopy() {
        return new ArrayList<Instruction>(_list);
    }
}

class EndSorter implements Comparator {
  public int compare(Object i1, Object i2) {
    return ((Long)((Instruction)i1).end()).compareTo(((Instruction)i2).end());
  }
}
