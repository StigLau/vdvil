package no.vdvil.renderer.lyric;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;


public class LyricInstruction extends Instruction {
    public final String text;

    public LyricInstruction(int start, int end, float bpm, final String text) {
        this.text = text;
        float speedFactor = Renderer.RATE * 60 / bpm;

        _start = new Float(start * speedFactor).intValue() ;
        _end = new Float(end * speedFactor).intValue();
    }
}