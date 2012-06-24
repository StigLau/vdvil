package no.bouvet.kpro.renderer;

import no.lau.vdvil.renderer.Renderer;
import no.lau.vdvil.instruction.Instruction;

/**
 * The AbstractRenderer class represents a renderer capable of rendering a
 * particular media type. Specializations of the AbstractRenderer class are
 * added to the master OldRenderer instance, and are pushed Instructions that they
 * may choose to act upon.
 * 
 * An AbstractRenderer has state, either started or stopped. It will only
 * receive AbstractInstruction events while started.
 * 
 * One AbstractRenderer in the render graph must be the time source. The master
 * OldRenderer instance will ask each AbstracRenderer to become the time source
 * until one agrees. This occurs via the requestTimeSource() method. The
 * AbstractRenderer that has agreed to become the time source must call
 * notifyTime() and notifyFinished() on the master OldRenderer object to provide
 * timing information.
 * 
 * @author Michael Stokes
 * @author Stig Lau
 */
public abstract class AbstractRenderer implements Renderer {
	protected OldRenderer _renderer;

	/**
	 * Construct a new AbstractRenderer instance.
	 */
	public AbstractRenderer() {
	}

	/**
	 * Set the master OldRenderer that contains this AbstractRenderer. This method
	 * is called by the OldRenderer object itself.
	 * 
	 * @param renderer
	 *            The OldRenderer instance
	 */
	public void setRenderer(OldRenderer renderer) {
		_renderer = renderer;
	}

	/**
	 * Request that this AbstractRenderer become the time source. If it agrees
	 * by returning true, it must call notifyTime() and notifyFinished() on the
	 * OldRenderer object as appropriate.
	 * 
	 * @return true if this AbstractRenderer agrees to become the time source
	 */
	public boolean requestTimeSource() {
		return false;
	}

	/**
	 * Start this AbstractRenderer, at the given point in time.
     * TODO Perhaps to be removed!!
	 * 
	 * @param time
	 *            The time in samples when rendering begins
	 * @return true if the AbstractRenderer started
	 */
	public boolean start(int time) {
		return true;
	}

	/**
	 * Stop this AbstractRenderer.
	 */
	public void stop() {
	}

	/**
	 * Handle a rendering AbstractInstruction. This method is called by the master
	 * OldRenderer as time passes within the instruction list. The time parameter
	 * specifies the current rendering time. The instruction parameter specifies
	 * a rendering instruction event that has occurred.
	 * 
	 * The start time of the AbstractInstruction will always be less than or equal to
	 * the time parameter. If it were greater, it would not have occurred yet.
	 * 
	 * The start time of the AbstractInstruction may be significantly less than the time
	 * parameter when rendering begins, as instructions that have already
	 * started may need to be handled to bring the state up to date.
	 * 
	 * A given instruction will only be fired once between a call to start() and
	 * end(), i.e. within a rendering session.
	 * 
	 * The instruction parameter may be null, which indicates no more
	 * instructions.
	 * 
	 * @param time
	 *            the current rendering time in samples
	 * @param instruction
	 *            the instruction that has occurred, or null
	 */
    public abstract void notify(Instruction instruction, long time);

    public abstract boolean isRendering();

    public abstract void stop(Instruction instruction);
}
