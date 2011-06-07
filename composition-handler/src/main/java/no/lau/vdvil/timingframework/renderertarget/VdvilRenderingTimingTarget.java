package no.lau.vdvil.timingframework.renderertarget;


import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Instructions;
import no.lau.vdvil.timingframework.MasterBeatPattern;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;

public class VdvilRenderingTimingTarget extends TimingTargetAdapter {

    private Instructions instructions;
    private MasterBeatPattern beatPattern;

    private int instructionPointer;

    public VdvilRenderingTimingTarget(Instructions instructions, MasterBeatPattern beatPattern) {

        this.instructions = instructions;
        this.beatPattern = beatPattern;
        instructionPointer = 0;
    }

    public void begin(Animator source) {
        System.out.println("Begin");
    }
    
    public void end(Animator source) {
        System.out.println("End");
    }
    
    public void timingEvent(Animator source, double fraction) {
        System.out.println("Timing " + fraction);
        Instruction instruction= instructions.lock().get(instructionPointer);

        System.out.println("instruction.getStart() = " + instruction.getStart());
        System.out.println("fraction = " + fraction);

        if(instruction.getStart() < fraction) {
            System.out.println("Hooray " + instruction.toString());
            instructionPointer ++;
        }
        instructions.unlock();
    }
}
