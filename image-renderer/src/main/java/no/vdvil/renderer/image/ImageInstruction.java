package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;
import no.lau.vdvil.timing.MasterBeatPattern;
import java.io.InputStream;
import java.net.URL;


public class ImageInstruction extends Instruction {
    public final URL imageUrl;
    public InputStream cachedStream = null;

    private ImageInstruction(int start, int end, final URL imageUrl, InputStream cached) {
        super(start, end);
        this.imageUrl = imageUrl;
        this.cachedStream = cached;
    }
    //TODO Use MasterBeatPattern instead!
    public static ImageInstruction create(MasterBeatPattern mbp, URL imageUrl, InputStream cached) {
        float speedFactor = Renderer.RATE * 60 / mbp.bpmAt(mbp.fromBeat);
        int _start = new Float(mbp.fromBeat * speedFactor).intValue();
        int _end = new Float(mbp.toBeat * speedFactor).intValue();
        return new ImageInstruction(_start, _end, imageUrl, cached);
    }
}
