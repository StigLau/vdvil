package no.lau.vdvil.player;

/**
 * Main interface for playing Vdvil compositions
 */
public interface VdvilPlayer {
    void play(int startAt);
    void stop();
    boolean isPlaying();
}
