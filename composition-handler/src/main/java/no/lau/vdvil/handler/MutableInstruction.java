package no.lau.vdvil.handler;

/**
 * Created by stiglau on 29.07.14.
 */
public interface MutableInstruction {
    //Used to move the playback of a segment, when adding a composition to another
    void moveStart(int bpmCueDifference);
}
