package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;

import java.io.InputStream;
import java.net.URL;


public class ImageInstruction extends Instruction {
    public final InputStream imageStream;

    public ImageInstruction(int start, int end, float bpm, final InputStream imageStream) {
        this.imageStream = imageStream;
        float speedFactor = Renderer.RATE * 60 / bpm;

        _start = new Float(start * speedFactor).intValue() ;
        _end = new Float(end * speedFactor).intValue();
    }
}