package no.lau.vdvil.handler.persistence;

import no.lau.vdvil.timing.TimeInterval;

public interface CompositionInstruction {
    String id();
    TimeInterval timeInterval();
    int start();
    int cueDifference(); //An indicator that the start has been moved and the cue has to be recalculated
    int end();
    MultimediaReference dvl();
}
