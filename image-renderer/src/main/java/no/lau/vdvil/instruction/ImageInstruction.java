package no.lau.vdvil.instruction;

import no.bouvet.kpro.renderer.OldRenderer;
import no.lau.vdvil.timing.MasterBeatPattern;
import java.io.InputStream;
import java.net.URL;

/**
 * ImageInstruction v2
 * @author Stig Lau
 * @since June 2012
 */
public class ImageInstruction extends SuperInstruction {
    public final URL imageUrl;
    public final InputStream cachedStream;

    public ImageInstruction(long start, long length, URL imageUrl, InputStream cachedStream) {
        super(start, length);
        this.imageUrl = imageUrl;
        this.cachedStream = cachedStream;
    }

    @Deprecated
    public static ImageInstruction create(MasterBeatPattern mbp, URL imageUrl, InputStream cached) {
        float speedFactor = OldRenderer.RATE * 60 / mbp.bpmAt(mbp.fromBeat);
        int _start = new Float(mbp.fromBeat * speedFactor).intValue();
        int _end = new Float(mbp.toBeat * speedFactor).intValue();
        int lenght = _end - _start;
        return new ImageInstruction(_start, lenght, imageUrl, cached);
    }
}