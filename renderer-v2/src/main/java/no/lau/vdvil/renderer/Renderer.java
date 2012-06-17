package no.lau.vdvil.renderer;

public interface Renderer {
    void addInstruction(Instruction instruction);
    void notify(long beat);
}
