package no.lau.vdvil.instruction;

import no.bouvet.kpro.renderer.OldRenderer;
import no.lau.vdvil.cache.FileRepresentation;

/**
 * LyricInstruction V2
 * @author Stig Lau
 * @since June 2012
 */
public class LyricInstruction extends SuperInstruction {
    public final String text;

    public LyricInstruction(long start, long length, String text) {
        super(start, length, FileRepresentation.NULL);
        this.text = text;
    }

    public static LyricInstruction create(int start, int end, float bpm, final String text) {
        float speedFactor = OldRenderer.RATE * 60 / bpm;
        int _start = new Float(start * speedFactor).intValue();
        int _end = new Float(end * speedFactor).intValue();
        return new LyricInstruction(_start, _end-_start, text);
    }
}
