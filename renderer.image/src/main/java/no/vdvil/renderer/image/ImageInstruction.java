package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;


public class ImageInstruction extends Instruction {
    public final String url;

    public ImageInstruction(int start, int end, float bpm, final String url) {
        this.url = url;
        float speedFactor = Renderer.RATE * 60 / bpm;

        _start = new Float(start * speedFactor).intValue() ;
        _end = new Float(end * speedFactor).intValue();
    }
}