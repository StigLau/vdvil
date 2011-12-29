package no.bouvet.kpro.renderer;

import no.lau.vdvil.handler.InstructionInterface;
import no.lau.vdvil.handler.CompositionI;
import no.lau.vdvil.renderer.TimeSource;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.lau.vdvil.timing.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
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
    protected Set<RendererToken> _renderers;
	protected TimeSource _timeSource;

	protected boolean _rendering = false;
    Logger log = LoggerFactory.getLogger(getClass());

    public Renderer(CompositionI composition, Set<RendererToken> renderers) {
        this.composition = composition;
        this._renderers = renderers;
        _timeSource = setUpTimeSource(renderers);
    }

    /*
     * Set up who the RENDERER is for callbacks. Nasty stuff....
     * If the renderer wants to be a timesource and none already wants to
     */
    private TimeSource setUpTimeSource(Set<RendererToken> renderers) {
        for (RendererToken rendererToken : renderers) {
            if (rendererToken instanceof TimeSource) {
                TimeSource timeSource = (TimeSource) rendererToken;
                if (timeSource.requestTimeSource()) {
                    timeSource.setRenderer(this);
                    return timeSource;
                }
            }
        }
        throw new RuntimeException("No timesource found among renderers!");
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
        // Que up all non-timesource renderers and start timesource as the last
        //If failed starting. stop renderers, empty instructionlist and unlock instructions

        //TODO Note that if this takes some time, setting up instructions should have been done PRIOR to start. Perhaps in construction!
        _timeSource.start();
        for (RendererToken renderer : _renderers) {
            if (renderer instanceof InstructionInterface) {
                ((InstructionInterface) renderer).setComposition(composition, playBackPattern);
            }
        }
        _rendering = true;
		return true;
	}

    /**
	 * Stop rendering.
	 */
	public synchronized void stop() {
        _timeSource.stop();
        for (RendererToken renderer : _renderers) {
            if(!(renderer instanceof TimeSource)) {
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
		return _rendering;
	}

	/**
	 * Notify the Renderer that a particular moment in time has been reached.
	 * This method is called by the AbstractRenderer that has agreed to become
	 * the time source.
	 * 
	 * @param time the time that has been reached
	 */
	public void notifyTime(Time time) {
        for (RendererToken rendererToken : _renderers) {
            ((InstructionInterface)rendererToken).ping(time);
        }
        //Remember stopInstruction!!!
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
	 * Keeps all renderers updated of the current time
	 * 
	 * @param time the current rendering time
	 */
	protected void dispatchInstruction(Time time) {
		for (RendererToken rendererToken : _renderers) {
            ((InstructionInterface)rendererToken).ping(time);
		}
	}

    private void dispatchStopInstruction(Instruction stopInstruction) {
        for (RendererToken renderer : _renderers) {
            renderer.stop(stopInstruction);
        }
    }
}
