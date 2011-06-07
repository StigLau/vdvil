package no.lau.vdvil.timingframework;

import no.bouvet.kpro.renderer.Instruction;

import java.net.URL;

public class SimpleInstruction extends Instruction {
    private URL imageUrl;

    public SimpleInstruction(int start, int end, final URL imageUrl) {
        super._start = start;
        super._end = end;
        this.imageUrl = imageUrl;
    }
}
