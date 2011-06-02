package no.bouvet.kpro.renderer;

public class StopInstruction extends Instruction {
    public StopInstruction(int end) {
        super._start = end;
        super._end = end;
    }

}
