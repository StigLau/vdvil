package no.lau.vdvil.renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple renderer that displays the current beat to logs, eventually maybe also with sound
 *
 * @author Stig Lau
 * @since June 2012
 */
public class MetronomeRenderer implements Renderer {

    final long start;
    final long length;
    static final Logger log = LoggerFactory.getLogger(MetronomeRenderer.class);

    public MetronomeRenderer(long start, long length) {
        this.start = start;
        this.length = length;
    }

    @Override
    public void notify(Instruction instruction, long beat) {
        if (start <= beat && beat <= start + length)
            log.info("Beat " + beat);
    }

    /**
     * Creates the appropriate set of instructions for rendering beats
     */
    public Instruction[] instructions() {
        MetInstruction[] instructions = new MetInstruction[(int) length];
        for (int i = 0; i < length; i++) {
            instructions[i] = new MetInstruction(start + i);

        }
        return instructions;
    }
}

class MetInstruction implements Instruction{

    final long start;

    public MetInstruction(long start) {
        this.start = start;
    }

    @Override
    public long start() {
        return start;
    }

    @Override
    public long length() {
        return 0;
    }
}

