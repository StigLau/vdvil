package no.lau.vdvil.timingframework.keyframe;

import org.jdesktop.core.animation.timing.Evaluator;

public class MyEvaluator implements Evaluator<MyInstruction> {
    /**
     * A problem is that the last one is never returned!!!
     */
    public MyInstruction evaluate(MyInstruction myInstruction, MyInstruction myInstruction1, double fraction) {
        return myInstruction;
    }

    public Class<MyInstruction> getEvaluatorClass() {
        return MyInstruction.class;
    }
}
