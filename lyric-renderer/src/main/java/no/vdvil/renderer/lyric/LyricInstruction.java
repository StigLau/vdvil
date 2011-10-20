package no.vdvil.renderer.lyric;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;

public class LyricInstruction extends Instruction {
    public final String text;

    private LyricInstruction(int start, int end, final String text) {
        super(start, end);
        this.text = text;
    }

    public static LyricInstruction create(int start, int end, float bpm, final String text) {
        float speedFactor = Renderer.RATE * 60 / bpm;
        int _start = new Float(start * speedFactor).intValue();
        int _end = new Float(end * speedFactor).intValue();
        return new LyricInstruction(_start, _end, text);
    }
}