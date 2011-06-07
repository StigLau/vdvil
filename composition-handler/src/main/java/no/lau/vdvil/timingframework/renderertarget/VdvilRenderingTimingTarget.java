package no.lau.vdvil.timingframework.renderertarget;


import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Instructions;
import no.lau.vdvil.timingframework.MasterBeatPattern;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;

import java.util.ArrayList;

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
        ArrayList<Instruction> instructionArrayList = instructions.lock();
        instructions.unlock();
        if(instructionPointer < instructionArrayList.size()) {// Avoid nullpointers
            Instruction instruction= instructionArrayList.get(instructionPointer);

            Float toStart = beatPattern.duration(0, instruction.getStart());
            System.out.println("fraction = " + fraction);
            System.out.println("toStart = " + toStart);
            Float comparedToAll = toStart / beatPattern.duration();
            System.out.println("comparedToAll = " + comparedToAll);
            if(fraction >= comparedToAll) {
                System.out.println("Hooray " + instruction.toString());
                instructionPointer ++;
            }
        }

    }
}
