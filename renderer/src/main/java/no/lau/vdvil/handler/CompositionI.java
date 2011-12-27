package no.lau.vdvil.handler;

import no.bouvet.kpro.renderer.Instructions;
import no.lau.vdvil.timing.MasterBeatPattern;

import java.io.IOException;

/**
 * Just a simple interface for a Composition
 */
@Deprecated //TODO Use something else!
public interface CompositionI {

    Instructions instructions(MasterBeatPattern beatPattern) throws IOException;
}
