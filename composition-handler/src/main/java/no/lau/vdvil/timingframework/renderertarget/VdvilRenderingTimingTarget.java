package no.lau.vdvil.timingframework.renderertarget;


import no.bouvet.kpro.renderer.Instruction;
import no.lau.vdvil.timingframework.MasterBeatPattern;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;

public class VdvilRenderingTimingTarget extends TimingTargetAdapter {

    private Instruction[] instructions;
    private MasterBeatPattern beatPattern;

    private int instructionPointer;

    public VdvilRenderingTimingTarget(Instruction[] instructions, MasterBeatPattern beatPattern) {
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
        if(instructionPointer < instructions.length) {// Avoid nullpointers
            Instruction instruction= instructions[instructionPointer];

            if(fraction >= beatPattern.percentage(instruction.getStart())) {
                System.out.println("Hooray " + instruction.toString());
                instructionPointer ++;
            }
        }

    }
}