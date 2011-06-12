package no.lau.vdvil.player;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;
import java.util.List;

/**
 * Simplified player for different instructions
 * Can be improved with a
 */
public class InstructionPlayer implements VdvilPlayer {

    final Renderer renderer;
    final int framerate = 44100;
    final Float masterBpm;

    public InstructionPlayer(Float masterBpm, Instructions instructions, List<? extends AbstractRenderer> renderers) {
        this.masterBpm = masterBpm;
        renderer = new Renderer(instructions);
        for (AbstractRenderer abstractRenderer : renderers) {
            renderer.addRenderer(abstractRenderer);
        }
    }

    public void play(int startAtBeat) {
        Float startCueInMillis = (startAtBeat * framerate * 60) / masterBpm;
        renderer.start(startCueInMillis.intValue());
    }

    public void stop() {
        renderer.stop();
    }

    public boolean isPlaying() {
        return renderer.isRendering();
    }
}
