package no.lau.vdvil.handler.persistence;

public interface CompositionInstruction {
    String id();
    int start();
    int end();
    MultimediaReference dvl();
}
