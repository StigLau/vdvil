package no.bouvet.kpro.renderer;

import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.renderer.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * The OldRenderer class represents the master renderer. It is responsible for
 * coordinating and scheduling the rendering process.
 * 
 * A OldRenderer instance is created by passing an Instructions list, containing
 * one or more rendering instructions. One or more AbstractRenderer instances
 * must then be added to the master OldRenderer using the addRenderer() method.
 * Each AbstractRenderer instance may support rendering of different media
 * types, and may understand different rendering instructions.
 * 
 * The OldRenderer dispatches rendering AbstractInstruction events to the connected
 * AbstractRenderers, based upon a time source that has been negotiated with one
 * of the AR's.
 * 
 * @author Michael Stokes
 * @author Stig Lau
 */
public class OldRenderer {
	protected ArrayList<Renderer> _renderers = new ArrayList<>();
	protected AbstractRenderer _timeSource;

	protected Instructions _instructions;
	protected List<Instruction> _instructionList;

    //Temporary lists used for housekeeping of what has played and stopped
    List<Instruction> played = new ArrayList<>();
    List<Instruction> stopped = new ArrayList<>();

	protected boolean _rendering = false;
    Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Construct a new OldRenderer, rendering the given Instructions list.
	 * 
	 * @param instructions the Instructions list to render
	 */
	public OldRenderer(Instructions instructions) {
		_instructions = instructions;
	}

    public void appendInstructions(Instructions instructions) {
        try {
            for (Instruction newInstruction : instructions.lock()) {
                _instructions.append(newInstruction);
            }
        }finally {
            instructions.unlock();
        }
    }

    /**
	 * Add an AbstractRenderer to this OldRenderer. Each AbstractRenderer will
	 * receive rendering AbstractInstruction events.
	 * 
	 * @param renderer the AbstractRenderer to add
	 */
	public synchronized void addRenderer(Renderer renderer) {
		stop();

		if (renderer instanceof AbstractRenderer && !_renderers.contains(renderer)) {
            AbstractRenderer abstractRenderer = (AbstractRenderer) renderer;
            _renderers.add(abstractRenderer);
            abstractRenderer.setRenderer(this);

            if (_timeSource == null) {
                if (abstractRenderer.requestTimeSource()) {
                    _timeSource = abstractRenderer;
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
	 * @param time the time in samples to start rendering
	 */
	public synchronized void start(int time) {
        log.info("Starting playback");
		stop();

		if (_renderers.isEmpty() || ((time < 0) || (time >= _instructions.getDuration()))) {
			fail();
		}
        if (_timeSource == null) {
			addRenderer(new TimeSourceRenderer());
		}

		_instructionList = _instructions.lock();
		if (_instructionList == null) {
            log.error("No instructions in list");
            fail();
        }

        log.info("Starting renderers that are not timesource");
		for (Renderer renderer : _renderers) {
            AbstractRenderer abstractRenderer = (AbstractRenderer) renderer;
			if (abstractRenderer != _timeSource) {
                log.info("{} started", abstractRenderer);
				abstractRenderer.start(time);
			}
		}

        log.info("TimeSource {} started", _timeSource);
        _timeSource.start(time);
		_rendering = true;
	}

    void fail() {
        stop();
        throw new RuntimeException("Renderer start failed. Cleaned up");
    }

	/**
     * Stop rendering.
     */
    public synchronized void stop() {
        log.info("Stopping renderer if started and cleaning up");

        if (_timeSource != null) {
            _timeSource.stop();
        }
        for (Renderer renderer : _renderers) {
            if (renderer != _timeSource) {
                ((AbstractRenderer) renderer).stop();
            }
        }

        played = new ArrayList<>();
        stopped = new ArrayList<>();
        _instructions.unlock();
    }

	/**
	 * Check if rendering is underway, and has not yet finished.
	 * 
	 * @return true if rendering is underway, and has not yet finished
	 */
	public boolean isRendering() {
		return _rendering;
	}

	/**
	 * Notify the OldRenderer that a particular moment in time has been reached.
	 * This method is called by the AbstractRenderer that has agreed to become
	 * the time source.
	 * 
	 * @param time the time that has been reached
	 */
    public void notifyTime(int time) {
        for (Instruction instruction : _instructionList) {
            if (!played.contains(instruction) && instruction.start() <= time) {
                log.info("Starting {} {}", instruction.getClass().getSimpleName(), instruction);
                dispatchInstruction(time, instruction);
                played.add(instruction);
            }
        }
        log.trace("Pruning list of played instructions");
        for (Instruction stopInstruction : played) {
            if(!stopped.contains(stopInstruction) && stopInstruction.end() <= time) {
                log.info("Stopping {} {}", stopInstruction.getClass().getSimpleName(), stopInstruction);
                dispatchStopInstruction(stopInstruction);
                stopped.add(stopInstruction);
            }
        }

        if(_instructionList.size() == played.size() && played.size() == stopped.size()) {
            log.info("Sending null instruction to stop");
            dispatchInstruction(time, null);
        }
	}


	/**
	 * Notify the OldRenderer that the last AbstractInstruction has finished rendering.
	 * This method is called by the AbstractRenderer that has agreed to become
	 * the time source.
	 */
	public void notifyFinished() {
        log.debug("Closing renderer playback!");
		_rendering = false;
	}

	/**
	 * Dispatch a given AbstractInstruction to all AbstractRenderers.
	 * 
	 * @param time the current rendering time
	 * @param instruction the instruction to dispatch, or null
	 */
	protected void dispatchInstruction(int time, Instruction instruction) {
		for (Renderer renderer : _renderers) {
			renderer.notify(instruction, time);
		}
	}

    private void dispatchStopInstruction(Instruction stopInstruction) {
        for (Renderer renderer : _renderers) {
            ((AbstractRenderer)renderer).stop(stopInstruction);
        }
    }
}
