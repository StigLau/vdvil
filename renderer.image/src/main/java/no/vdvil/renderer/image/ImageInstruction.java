package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;

import java.net.URL;


public class ImageInstruction extends Instruction {
    public final URL url;

    public ImageInstruction(int start, int end, float bpm, final URL url) {
        this.url = url;
        float speedFactor = Renderer.RATE * 60 / bpm;

        _start = new Float(start * speedFactor).intValue() ;
        _end = new Float(end * speedFactor).intValue();
    }
}