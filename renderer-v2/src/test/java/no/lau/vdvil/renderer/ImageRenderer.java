package no.lau.vdvil.renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageRenderer implements Renderer {

    static final Logger log = LoggerFactory.getLogger(ImageRenderer.class);
    final static String[] acceptHeaders = new String[]{"Image/png", "Image/jpg"};
    TimedInstructionStore instructionStore = new TimedInstructionStore();
    private final Renderer masterRenderer;

    public ImageRenderer(Renderer masterRenderer) {
        this.masterRenderer = masterRenderer;
    }

    public void addInstruction(Instruction instruction) {
        masterRenderer.addInstruction(instruction);
        instructionStore.put(instruction);
    }

    public void notify(long beat) {
        if(instructionStore.contains(beat)) {
            for (Instruction instruction : instructionStore.get(beat)) {
                log.info("Displaying {}", ((ImageInstruction) instruction).imageUrl);
            }
        }

    }
}
