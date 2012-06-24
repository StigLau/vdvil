package no.lau.vdvil.renderer;

import no.lau.vdvil.instruction.Instruction;

public interface Renderer {
    void notify(Instruction instruction, long beat);
}
