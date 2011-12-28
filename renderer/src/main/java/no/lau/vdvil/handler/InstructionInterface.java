package no.lau.vdvil.handler;

import no.bouvet.kpro.renderer.Instruction;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.lau.vdvil.timing.Time;

import java.io.IOException;

/**
 * An improved (12.2011) interface for Instructions
 */
public interface InstructionInterface {
    void ping(Time time);
    void setComposition(CompositionI composition, MasterBeatPattern beatPattern) throws IOException;
    void stop(Instruction instruction);
}
