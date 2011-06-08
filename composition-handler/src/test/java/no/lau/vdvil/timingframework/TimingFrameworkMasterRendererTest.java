package no.lau.vdvil.timingframework;

import no.bouvet.kpro.renderer.Instruction;
import no.lau.vdvil.player.VdvilPlayer;
import no.lau.vdvil.timingframework.keyframe.MyEvaluator;
import no.lau.vdvil.timingframework.keyframe.MyInstruction;
import no.lau.vdvil.timingframework.keyframe.MyKeyFrameTarget;
import no.lau.vdvil.timingframework.renderertarget.VdvilRenderingTimingTarget;
import org.jdesktop.core.animation.timing.KeyFrames;
import org.jdesktop.core.animation.timing.KeyFramesTimingTarget;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class TimingFrameworkMasterRendererTest {

    @Test
    public void startItUp() throws InterruptedException, MalformedURLException {
        Instruction[] instructions = new Instruction[]{
                new SimpleInstruction(0, 8, new URL("http://www.yo.com")),
                new SimpleInstruction(4, 12, new URL("http://www.yu.com")),
                new SimpleInstruction(8, 16, new URL("http://www.yui.com"))
        };
        MasterBeatPattern beatPattern = new MasterBeatPattern(0, 16, 120F);

        VdvilPlayer timingPlayer = new TimingFrameworkMasterRenderer(beatPattern,
                new VdvilRenderingTimingTarget(instructions, beatPattern), keyframeTestData());

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

