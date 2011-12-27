package no.bouvet.kpro.renderer;

import no.lau.vdvil.handler.CompositionI;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;
import java.util.Set;

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
 * @author Stig Lau
 */
public class Renderer {
	/**
	 * The fundamental time unit. There are RATE units per second.
	 */
	public final static int RATE = 44100;

    private CompositionI composition;
    protected Set<? extends AbstractRenderer> _renderers;
	protected AbstractRenderer _timeSource;

	List<Instruction> _instructionList;
    List<Instruction> stopInstructionSortedByEnd;
	protected int _instructionPtr;
    protected int stopInstructionPtr;
	protected boolean _rendering = false;
    Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Construct a new Renderer, rendering the given Instructions list.
	 * 
	 * @param instructions the Instructions list to render
	 */
    @Deprecated
	public Renderer(Instructions instructions, AbstractRenderer singleRenderer) {
	}

    public Renderer(CompositionI composition, Set<? extends AbstractRenderer> renderers) {
        this.composition = composition;
        this._renderers = renderers;
        for (AbstractRenderer renderer : renderers) {
            setUpRenderers(renderer);
        }
    }

    /*
     * Set up who the RENDERER is for callbacks. Nasty stuff....
     * If the renderer wants to be a timesource and none already wants to
     */
    private void setUpRenderers(AbstractRenderer renderer) {
        renderer.setRenderer(this);
        if (_timeSource == null && renderer.requestTimeSource())
            _timeSource = renderer;
    }


    /**
	 * Start rendering at the given time. - The Instructions list must be
	 * non-empty and have a non-zero duration - There must be at least one
	 * AbstractRenderer connected - All connected AbstractRenderers must agree
	 * to start - The time parameter must lie within the duration of the
	 * Instructions list
	 *
     * @param playBackPattern how should the composition be played back
     * @return true if rendering started
	 */
	public synchronized boolean start(MasterBeatPattern playBackPattern) {
        stop();
        // Que up all non-timesource renderers and start timesource as the last
        //If failed starting. stop renderers, empty instructionlist and unlock instructions

        int time = calculateTime(playBackPattern);

        try {
            Instructions instructions = composition.instructions(playBackPattern);
            this._instructionList = instructions._list;
            this.stopInstructionSortedByEnd = instructions.sortedByEnd();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        //TODO Note that if this takes some time, setting up instructions should have been done PRIOR to start. Perhaps in construction!
        for (AbstractRenderer renderer : _renderers) {
            for (Instruction instruction : _instructionList) {
                renderer.handleInstruction(time, instruction);
            }
            renderer.start(time);
        }


		_rendering = true;
		return true;
	}

    /*
     * Quick fix to make Renderer work the old fashioned way until it has been cleaned out
     */
    @Deprecated
    private int calculateTime(MasterBeatPattern playBackPattern) {
         MasterBeatPattern untilStart = new MasterBeatPattern(0, playBackPattern.fromBeat, playBackPattern.masterBpm);
        //TODO Note that there are potential problems here!!!
        final int framerate = 44100;
        Float duration = untilStart.durationCalculation() * framerate / 1000;
        return duration.intValue();
    }

    /**
	 * Stop rendering.
	 */
	public synchronized void stop() {
			_timeSource.stop();

        for (AbstractRenderer renderer : _renderers) {
            if (renderer != _timeSource) {
                renderer.stop();
            }
        }
	}

	/**
	 * Check if rendering is underway, and has not yet finished.
	 * 
	 * @return true if rendering is underway, and has not yet finished
	 */
	public synchronized boolean isRendering() {
		return (_instructionList != null) && _rendering;
	}

	/**
	 * Notify the Renderer that a particular moment in time has been reached.
	 * This method is called by the AbstractRenderer that has agreed to become
	 * the time source.
	 * 
	 * @param time the time that has been reached
	 */
	public void notifyTime(int time) {
        if(_instructionPtr < _instructionList.size()) {
            Instruction instruction = _instructionList.get(_instructionPtr);

            if (instruction._start <= time) {
                dispatchInstruction(time, instruction);
                _instructionPtr++;
            }
        }
        if(stopInstructionPtr < stopInstructionSortedByEnd.size()) {
            Instruction stopInstruction = stopInstructionSortedByEnd.get(stopInstructionPtr);
            if (stopInstruction._end <= time) {
                dispatchStopInstruction(stopInstruction);
                stopInstructionPtr++;
            }
        }
        //Dispatch that all instructions have been sent!
        if (_instructionPtr >= _instructionList.size()) {
            dispatchInstruction(time, null);
        }
	}

	/**
	 * Notify the Renderer that the last Instruction has finished rendering.
	 * This method is called by the AbstractRenderer that has agreed to become
	 * the time source.
	 */
	public void notifyFinished() {
        log.debug("Closing renderer playback!");
		_rendering = false;
	}

	/**
	 * Dispatch a given Instruction to all AbstractRenderers.
	 * 
	 * @param time the current rendering time
	 * @param instruction the instruction to dispatch, or null
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
