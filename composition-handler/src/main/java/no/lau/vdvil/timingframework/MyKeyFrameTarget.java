package no.lau.vdvil.timingframework;

import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.KeyFrames;
import org.jdesktop.core.animation.timing.KeyFramesTimingTarget;

public class MyKeyFrameTarget extends KeyFramesTimingTarget<MyInstruction> {

    public MyKeyFrameTarget(KeyFrames<MyInstruction> myInstructionKeyFrames) {
        super(myInstructionKeyFrames);
    }

    @Override
    public void valueAtTimingEvent(MyInstruction myInstruction, double fraction, Animator source) {
        System.out.println(myInstruction.value + " " + fraction );
    }
}
