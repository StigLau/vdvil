package no.lau.vdvil.handler.persistence;

public interface MutableCompositionInstruction {
    void setStart(int startBeat);
    void setEnd(int endBeat);
}
