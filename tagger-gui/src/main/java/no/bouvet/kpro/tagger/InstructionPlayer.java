package no.bouvet.kpro.tagger;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;
import java.util.List;

/**
 * Simplified player for different instructions
 * Can be improved with a
 */
public class InstructionPlayer {

    final Renderer renderer;

    public InstructionPlayer(Instructions instructions, List<AbstractRenderer> renderers) {
        renderer = new Renderer(instructions);
        for (AbstractRenderer abstractRenderer : renderers) {
            renderer.addRenderer(abstractRenderer);
        }
    }

    public void play(int startAt) {
        renderer.start(startAt);
    }

    public void stop() {
        renderer.stop();
    }
}
