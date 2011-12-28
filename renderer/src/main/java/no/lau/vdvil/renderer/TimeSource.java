package no.lau.vdvil.renderer;

import no.bouvet.kpro.renderer.Renderer;

/**
 * Created by IntelliJ IDEA.
 * User: stiglau
 * Date: 28.12.11
 * Time: 17.03
 * To change this template use File | Settings | File Templates.
 */
public interface TimeSource {

    /**
     * Request that this AbstractRenderer become the time source. If it agrees
     * by returning true, it must call notifyTime() and notifyFinished() on the
     * Renderer object as appropriate.
     *
     * @return true if this AbstractRenderer agrees to become the time source
     * @author Michael Stokes
     */
    boolean requestTimeSource();
    void stop();
    void start(int time) ;
    /**
     * Set the master Renderer that contains this AbstractRenderer. This method
     * is called by the Renderer object itself.
     * @author Michael Stokes
     */
    void setRenderer(Renderer renderer);
}
