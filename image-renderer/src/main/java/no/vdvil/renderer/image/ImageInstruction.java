package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.net.URL;


public class ImageInstruction extends Instruction {
    public final URL imageUrl;
    public InputStream cachedStream = null;
    static Logger log = LoggerFactory.getLogger(ImageInstruction.class);

    private ImageInstruction(int start, int end, final URL imageUrl, InputStream cached) {
        super(start, end);
        this.imageUrl = imageUrl;
        this.cachedStream = cached;
    }

    public static ImageInstruction create(int start, int end, float bpm, URL imageUrl, InputStream cached) {
        float speedFactor = Renderer.RATE * 60 / bpm;
        //An uncertain problem that appends ~44100 millseconds to everything. Weired!
        //TODO FIX TIMING ISSUES HERE!!!!!!!!!!!!!!!
        int _start = new Float(start * speedFactor).intValue();
        log.info("bpm * 44100 / 120 = " + bpm);
        int _end = new Float(end * speedFactor).intValue();
        return new ImageInstruction(_start, _end, imageUrl, cached);
    }
}
