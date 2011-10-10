package no.bouvet.kpro.renderer;

import java.util.ArrayList;
import java.util.List;

/**
 * The Renderer class represents the master renderer. It is responsible for
 * coordinating and scheduling the rendering process.
 * 
 * A Renderer instance is created by passing an Instructions list, containing
 * one or more rendering instructions. One or more AbstractRenderer instances
 * must then be added to the master Renderer using the addRenderer() method.
 * Each AbstractRenderer instance may support rendering of different media
 * types, and may understand different rendering instructions.
 * 
 * The Renderer dispatches rendering Instruction events to the connected
 * AbstractRenderers, based upon a time source that has been negotiated with one
 * of the AR's.
 * 
 * @author Michael Stokes
 */
public class Renderer {
	/**
	 * The fundamental time unit. There are RATE units per second.
	 */
	public final static int RATE = 44100;

	protected ArrayList<AbstractRenderer> _renderers = new ArrayList<AbstractRenderer>();
	protected AbstractRenderer _timeSource;

	protected Instructions _instructions;
	protected ArrayList<Instruction> _instructionList;
	protected int _instructionPtr;
    List<Instruction> stopInstructionList = new ArrayList<Instruction>();
    protected int stopInstructionPtr;
	protected boolean _rendering = false;

	/**
	 * Construct a new Renderer, rendering the given Instructions list.
	 * 
	 * @param instructions
	 *            the Instructions list to render
	 * @author Michael Stokes
	 */
	public Renderer(Instructions instructions) {
		_instructions = instructions;
        stopInstructionList = sortedInstructionsOnEnd(instructions._list);
	}

    /**
     * Important that the stopList is sorted correctly by stoptime
     * TODO Sort this list correctly!!!!!!!!!!
     */
    ArrayList<Instruction> sortedInstructionsOnEnd(ArrayList<Instruction> instructions) {
        return instructions;
    }

    /**
	 * Add an AbstractRenderer to this Renderer. Each AbstractRenderer will
	 * receive rendering Instruction events.
	 * 
	 * @param renderer
	 *            the AbstractRenderer to add
	 * @author Michael Stokes
	 */
	public synchronized void addRenderer(AbstractRenderer renderer) {
		stop();

		if (!_renderers.contains(renderer)) {
			_renderers.add(renderer);
			renderer.setRenderer(this);

			if (_timeSource == null) {
				if (renderer.requestTimeSource()) {
					_timeSource = renderer;
				}
			}
		}
	}

	/**
	 * Start rendering at the given time. - The Instructions list must be
	 * non-empty and have a non-zero duration - There must be at least one
	 * AbstractRenderer connected - All connected AbstractRenderers must agree
	 * to start - The time parameter must lie within the duration of the
	 * Instructions list
	 * 
	 * @param time
	 *            the time in samples to start rendering
	 * @return true if rendering started
	 * @author Michael Stokes
	 */
	public synchronized boolean start(int time) {
		stop();

		if (_renderers.isEmpty()) {
			return false;
		} else if ((time < 0) || (time >= _instructions.getDuration())) {
			return false;
		} else if (_timeSource == null) {
			addRenderer(new TimeSourceRenderer());
		}

		_instructionList = _instructions.lock();
		if (_instructionList == null)
			return false;

		for (_instructionPtr = 0; _instructionPtr < _instructionList.size(); _instructionPtr++) {
			Instruction instruction = _instructionList.get(_instructionPtr);
			if (instruction.getEnd() > time)
				break;
		}

		if (_instructionPtr >= _instructionList.size()) {
			_instructionList = null;
			_instructions.unlock();

			return false;
		}

		ArrayList<AbstractRenderer> started = new ArrayList<AbstractRenderer>();
		boolean failure = false;

		for (AbstractRenderer renderer : _renderers) {
			if (renderer != _timeSource) {
				if (renderer.start(time)) {
					started.add(renderer);
				} else {
					failure = true;
					break;
				}
			}
		}

		if (!failure) {
			if (_timeSource.start(time)) {
				started.add(_timeSource);
			} else {
				failure = true;
			}
		}

		if (failure) {
			for (AbstractRenderer renderer : started) {
				renderer.stop();
			}

			_instructionList = null;
			_instructions.unlock();

			return false;
		}

		_rendering = true;

		return true;
	}

	/**
	 * Stop rendering.
	 * 
	 * @author Michael Stokes
	 */
	public synchronized void stop() {
		if (_instructionList != null) {
			_timeSource.stop();

			for (AbstractRenderer renderer : _renderers) {
				if (renderer != _timeSource) {
					renderer.stop();
				}
			}

			_instructionList = null;
			_instructions.unlock();
		}
	}

	/**
	 * Check if rendering is underway, and has not yet finished.
	 * 
	 * @return true if rendering is underway, and has not yet finished
	 * @author Michael Stokes
	 */
	public synchronized boolean isRendering() {
		return (_instructionList != null) && _rendering;
	}

	/**
	 * Notify the Renderer that a particular moment in time has been reached.
	 * This method is called by the AbstractRenderer that has agreed to become
	 * the time source.
	 * 
	 * @param time
	 *            the time that has been reached
	 * @author Michael Stokes
	 */
	public void notifyTime(int time) {
		if (_instructionList != null) {
			if(_instructionPtr < _instructionList.size()) {
				Instruction instruction = _instructionList.get(_instructionPtr);

				if (instruction.getStart() <= time) {
					dispatchInstruction(time, instruction);
                    _instructionPtr++;
				}
			}
            if(stopInstructionPtr < stopInstructionList.size()) {
				Instruction stopInstruction = stopInstructionList.get(stopInstructionPtr);
				if (stopInstruction.getEnd() <= time) {
                    dispatchStopInstruction(stopInstruction);
                    stopInstructionPtr++;
				}
            }

			if (_instructionPtr >= _instructionList.size()) {
				dispatchInstruction(time, null);
			}
		}
	}

	/**
	 * Notify the Renderer that the last Instruction has finished rendering.
	 * This method is called by the AbstractRenderer that has agreed to become
	 * the time source.
	 * 
	 * @author Michael Stokes
	 */
	public void notifyFinished() {
		_rendering = false;
	}

	/**
	 * Dispatch a given Instruction to all AbstractRenderers.
	 * 
	 * @param time
	 *            the current rendering time
	 * @param instruction
	 *            the instruction to dispatch, or null
	 * @author Michael Stokes
	 */
	protected void dispatchInstruction(int time, Instruction instruction) {
		for (AbstractRenderer renderer : _renderers) {
			renderer.handleInstruction(time, instruction);
		}
	}

    private void dispatchStopInstruction(Instruction stopInstruction) {
            for (AbstractRenderer renderer : _renderers) {
                renderer.stop(stopInstruction);
            }
        }
}
