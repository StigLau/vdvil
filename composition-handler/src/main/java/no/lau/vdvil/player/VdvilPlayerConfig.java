package no.lau.vdvil.player;

import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.renderer.Renderer;
import java.util.List;

/**
 * Interface for configuring parsers and renderers.
 * @author Stig@Lau.no - 27/12/14.
 */
public interface VdvilPlayerConfig {
    public ParseFacade getParseFacade() ;

    public List<Renderer> getRenderers() ;
}
