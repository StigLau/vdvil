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

    private long start;
    private long end;
    static final Logger log = LoggerFactory.getLogger(MetronomeRenderer.class);

    public void addInstruction(Instruction instruction) {
        start = instruction.start();
        end = start + instruction.length();
    }

    @Override
    public void notify(long beat) {
        if (start <= beat && beat <= end)
            log.info("Beat " + beat);
    }
}

class MetronomeInstruction implements Instruction{

    final long start;
    final long length;

    public MetronomeInstruction(long start, long length) {
        this.start = start;
        this.length = length;
    }

    public long start() {
        return start;
    }

    public long length() {
        return length;
    }
}