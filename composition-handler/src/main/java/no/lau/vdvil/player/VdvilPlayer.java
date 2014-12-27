package no.lau.vdvil.player;

/**
 * Main interface for playing Vdvil compositions
 */
public interface VdvilPlayer {
    VdvilPlayer play();
    void playUntilEnd();
    VdvilPlayer stop();
    boolean isPlaying();
}
