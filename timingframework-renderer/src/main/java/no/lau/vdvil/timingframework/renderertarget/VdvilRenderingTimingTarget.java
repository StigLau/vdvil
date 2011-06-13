package no.lau.vdvil.timingframework.renderertarget;


import no.lau.vdvil.timing.MasterBeatPattern;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.TimingSource;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;
import org.jdesktop.swing.animation.timing.sources.SwingTimerTimingSource;
import java.util.concurrent.TimeUnit;

public class VdvilRenderingTimingTarget extends TimingTargetAdapter {

    private TimingInstruction[] instructions;
    private MasterBeatPattern beatPattern;

    private int instructionPointer;

    public VdvilRenderingTimingTarget(TimingInstruction[] instructions, MasterBeatPattern beatPattern) {
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
            TimingInstruction instruction= instructions[instructionPointer];

            if(fraction >= beatPattern.percentage(instruction.beatPattern.fromBeat)) {
                //TimingSource timingSource = new ScheduledExecutorTimingSource();
                TimingSource timingSource = new SwingTimerTimingSource();
                timingSource.init();
                Animator animator = new Animator.Builder(timingSource)
                        .setDuration(instruction.beatPattern.durationCalculation().longValue(), TimeUnit.MILLISECONDS)
                        .addTarget(instruction.renderingTarget)
                        .build();
                animator.start();
                instructionPointer ++;
            }
        }
    }
}
