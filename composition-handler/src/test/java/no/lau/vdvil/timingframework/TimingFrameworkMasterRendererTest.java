package no.lau.vdvil.timingframework;

import no.bouvet.kpro.renderer.Instructions;
import no.lau.vdvil.player.VdvilPlayer;
import no.lau.vdvil.timingframework.keyframe.MyEvaluator;
import no.lau.vdvil.timingframework.keyframe.MyKeyFrameTarget;
import no.lau.vdvil.timingframework.renderertarget.VdvilRenderingTimingTarget;
import org.jdesktop.core.animation.timing.KeyFrames;
import org.jdesktop.core.animation.timing.KeyFramesTimingTarget;
import org.jdesktop.core.animation.timing.TimingTarget;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class TimingFrameworkMasterRendererTest {

    @Test
    public void startItUp() throws InterruptedException, MalformedURLException {
        KeyFramesTimingTarget keyFramesTimingTarget = keyframeTestData();


        Instructions instructions = new Instructions();
        instructions.append(new SimpleInstruction(0, 16, new URL("http://www.yo.com")));
        MasterBeatPattern beatPattern = new MasterBeatPattern(0, 16, 120F);
        TimingTarget vdvilTimingTarget = new VdvilRenderingTimingTarget(instructions, beatPattern);


        VdvilPlayer timingPlayer = new TimingFrameworkMasterRenderer(beatPattern, keyFramesTimingTarget,  vdvilTimingTarget);


        timingPlayer.play(0);
        while(timingPlayer.isPlaying()) {
            Thread.sleep(1000);
        }
    }

    private KeyFramesTimingTarget keyframeTestData() {
        KeyFrames.Builder<MyInstruction> builder = new KeyFrames.Builder<MyInstruction>();
        builder.addFrame(new MyInstruction("a"), 0.0D);
        builder.addFrame(new MyInstruction("b"), 0.2D);
        builder.addFrame(new MyInstruction("c"), 0.5D);
        builder.addFrame(new MyInstruction("d"), 0.7D);
        builder.addFrame(new MyInstruction("e"), 1D); //NOTE THE LAST ONE DOES NOT PLAY!!!!
        builder.setEvaluator(new MyEvaluator());
        KeyFrames<MyInstruction> keyFrames = builder.build();
        return new MyKeyFrameTarget(keyFrames);
    }
}

