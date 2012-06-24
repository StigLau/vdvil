package no.lau.vdvil.renderer;

import no.lau.vdvil.instruction.Instruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class ImageRenderer implements Renderer {

    static final Logger log = LoggerFactory.getLogger(ImageRenderer.class);
    final static String[] acceptHeaders = new String[]{"Image/png", "Image/jpg"};

    public void notify(Instruction instruction, long beat) {
        log.info("Displaying {}", ((ImageInstruction) instruction).imageUrl);
    }
}
