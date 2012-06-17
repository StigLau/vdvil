package no.lau.vdvil.renderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for keeping track of all events to be handled by renderers
 * @author Stig Lau
 * @since June 2012
 */
public class MasterRenderer implements Renderer {
    TimedInstructionStore instructionStore = new TimedInstructionStore();
    private List<Renderer> renderers = new ArrayList<Renderer>();

    public void notify(long beat) {
        for (Renderer renderer : renderers) {
            renderer.notify(beat);
        }
    }

    public void addInstruction(Instruction instruction) {
        instructionStore.put(instruction);
    }

    public void addRenderer(Renderer renderer) {
        this.renderers.add(renderer);
    }
}
