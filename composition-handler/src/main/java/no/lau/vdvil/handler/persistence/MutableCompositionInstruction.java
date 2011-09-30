package no.lau.vdvil.handler.persistence;

public interface MutableCompositionInstruction {
    void moveStart(int cueDifference);
    void setEnd(int endBeat);
}
