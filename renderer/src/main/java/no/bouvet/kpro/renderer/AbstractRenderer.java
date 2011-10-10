package no.bouvet.kpro.renderer;

/**
 * The AbstractRenderer class represents a renderer capable of rendering a
 * particular media type. Specializations of the AbstractRenderer class are
 * added to the master Renderer instance, and are pushed Instructions that they
 * may choose to act upon.
 * 
 * An AbstractRenderer has state, either started or stopped. It will only
 * receive Instruction events while started.
 * 
 * One AbstractRenderer in the render graph must be the time source. The master
 * Renderer instance will ask each AbstracRenderer to become the time source
 * until one agrees. This occurs via the requestTimeSource() method. The
 * AbstractRenderer that has agreed to become the time source must call
 * notifyTime() and notifyFinished() on the master Renderer object to provide
 * timing information.
 * 
 * @author Michael Stokes
 */
public abstract class AbstractRenderer {
	protected Renderer _renderer;

	/**
	 * Construct a new AbstractRenderer instance.
	 * 
	 * @author Michael Stokes
	 */
	public AbstractRenderer() {
	}

	/**
	 * Set the master Renderer that contains this AbstractRenderer. This method
	 * is called by the Renderer object itself.
	 * 
	 * @param renderer
	 *            The Renderer instance
	 * @author Michael Stokes
	 */
	public void setRenderer(Renderer renderer) {
		_renderer = renderer;
	}

	/**
	 * Request that this AbstractRenderer become the time source. If it agrees
	 * by returning true, it must call notifyTime() and notifyFinished() on the
	 * Renderer object as appropriate.
	 * 
	 * @return true if this AbstractRenderer agrees to become the time source
	 * @author Michael Stokes
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
	 * @author Michael Stokes
	 */
	public boolean start(int time) {
		return true;
	}

	/**
	 * Stop this AbstractRenderer.
	 * 
	 * @author Michael Stokes
	 */
	public void stop() {
	}

	/**
	 * Handle a rendering Instruction. This method is called by the master
	 * Renderer as time passes within the instruction list. The time parameter
	 * specifies the current rendering time. The instruction parameter specifies
	 * a rendering instruction event that has occurred.
	 * 
	 * The start time of the Instruction will always be less than or equal to
	 * the time parameter. If it were greater, it would not have occurred yet.
	 * 
	 * The start time of the Instruction may be significantly less than the time
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
	 * @author Michael Stokes
	 */
	public abstract void handleInstruction(int time, Instruction instruction);

    public abstract boolean isRendering();

    public abstract void stop(Instruction instruction);
}
