package no.bouvet.kpro.renderer.simple;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;


public class SimpleLyricInstruction extends Instruction {
    public final String text;

    public SimpleLyricInstruction(Float start, Float end, float bpm, final String text) {
        this.text = text;
        float speedFactor = Renderer.RATE * 60 / bpm;

        _start = new Float(start * speedFactor).intValue() ;
        _end = new Float(end * speedFactor).intValue();
    }
}