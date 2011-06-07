package no.lau.vdvil.timingframework;

import no.lau.vdvil.player.VdvilPlayer;
import org.jdesktop.core.animation.timing.KeyFrames;
import org.jdesktop.core.animation.timing.KeyFramesTimingTarget;
import org.junit.Test;

public class TimingFrameworkMasterRendererTest {

    @Test
    public void startItUp() throws InterruptedException {
        KeyFrames.Builder<MyInstruction> builder = new KeyFrames.Builder<MyInstruction>();
        builder.addFrame(new MyInstruction("a"), 0.0D);
        builder.addFrame(new MyInstruction("b"), 0.2D);
        builder.addFrame(new MyInstruction("c"), 0.5D);
        builder.addFrame(new MyInstruction("d"), 0.7D);
        builder.addFrame(new MyInstruction("e"), 1D); //NOTE THE LAST ONE DOES NOT PLAY!!!!
        builder.setEvaluator(new MyEvaluator());
        KeyFrames<MyInstruction> keyFrames = builder.build();

        KeyFramesTimingTarget timingTarget = new MyKeyFrameTarget(keyFrames);
        VdvilPlayer timingPlayer = new TimingFrameworkMasterRenderer(0, 16, new MasterBpm(120F), timingTarget);

        timingPlayer.play(0);
        while(timingPlayer.isPlaying()) {
            Thread.sleep(1000);
        }
    }
}

