package no.lau.vdvil.instruction;

import java.io.InputStream;
import java.net.URL;

/**
 * ImageInstruction v2
 * @author Stig Lau
 * @since June 2012
 */
public class ImageInstruction implements Instruction {
    final long start;
    final long length;
    public final URL imageUrl;
    public final InputStream cachedStream;

    public ImageInstruction(long start, long length, URL imageUrl, InputStream cachedStream) {
        this.start = start;
        this.length = length;
        this.imageUrl = imageUrl;
        this.cachedStream = cachedStream;
    }

    @Override
    public long start() {
        return start;
    }

    @Override
    public long length() {
        return length;
    }
}


