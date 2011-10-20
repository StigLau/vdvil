package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;
import no.lau.vdvil.timing.MasterBeatPattern;
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

    public static ImageInstruction create(MasterBeatPattern mbp, URL imageUrl, InputStream cached) {
        float speedFactor = Renderer.RATE * 60 / mbp.bpmAt(mbp.fromBeat);
        //An uncertain problem that appends ~44100 millseconds to everything. Weired!
        //TODO FIX TIMING ISSUES HERE!!!!!!!!!!!!!!!
        int _start = new Float(mbp.fromBeat * speedFactor).intValue();
        int _end = new Float(mbp.toBeat * speedFactor).intValue();
        return new ImageInstruction(_start, _end, imageUrl, cached);
    }
}
