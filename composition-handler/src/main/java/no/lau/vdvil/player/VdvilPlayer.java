package no.lau.vdvil.player;

/**
 * Main interface for playing Vdvil compositions
 */
public interface VdvilPlayer {
    void play();
    void stop();
    boolean isPlaying();

    VdvilPlayer NULL = new VdvilPlayer() {
        public void play() { throw new RuntimeException("NULL PLAYER USED - CHECK USAGE!"); }
        public void stop() { throw new RuntimeException("Null Player"); }
        public boolean isPlaying() { return false; }
    };
}
