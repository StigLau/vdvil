package no.lau.vdvil.handler.persistence;

public interface CompositionInstruction {
    String id();
    int start();
    int cueDifference(); //An indicator that the start has been moved and the cue has to be recalculated
    int end();
    MultimediaReference dvl();
}
