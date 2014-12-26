package no.lau.vdvil.player;

import no.lau.vdvil.handler.Composition;

/**
 * Main interface for playing Vdvil compositions
 */
public interface VdvilPlayer {
    VdvilPlayer init(Composition composition);
    VdvilPlayer play();
    void playUntilEnd();
    VdvilPlayer stop();
    boolean isPlaying();

    VdvilPlayer NULL = new VdvilPlayer() {
        public VdvilPlayer init(Composition composition) { throw new RuntimeException("NULL PLAYER USED - CHECK USAGE!"); }
        public VdvilPlayer play() { throw new RuntimeException("NULL PLAYER USED - CHECK USAGE!"); }
        public void playUntilEnd() { throw new RuntimeException("NULL PLAYER USED - CHECK USAGE!"); }
        public VdvilPlayer stop() { throw new RuntimeException("Null Player"); }
        public boolean isPlaying() { return false; }
    };
}
