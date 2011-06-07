package no.lau.vdvil.timingframework;

import no.lau.vdvil.player.VdvilPlayer;
import org.jdesktop.core.animation.timing.*;
import org.jdesktop.core.animation.timing.sources.ScheduledExecutorTimingSource;
import java.util.concurrent.TimeUnit;

public class TimingFrameworkMasterRenderer implements VdvilPlayer {

    final Animator animator;
    final TimingSource scheduler;

    public TimingFrameworkMasterRenderer(int start, int end, MasterBpm masterBpm, TimingTarget... timingTargets) {
        scheduler = new ScheduledExecutorTimingSource(200, TimeUnit.MILLISECONDS);
        animator = createAnimator(start, end, masterBpm, timingTargets);
    }

    public void play(int startAtBeat) {
        scheduler.init();
        animator.start();
    }

    public void stop() {
        animator.cancel();
    }

    public boolean isPlaying() {
        return animator.isRunning();
    }

    private Animator createAnimator(int start, int end, MasterBpm masterBpm, TimingTarget... timingTargets) {
        Float duration = masterBpm.duration(start, end);
        Animator.Builder animatorBuilder = new Animator.Builder(scheduler).setDuration(duration.longValue(), TimeUnit.MILLISECONDS);
        for (TimingTarget timingTarget : timingTargets) {
            animatorBuilder.addTarget(timingTarget);
        }
        return animatorBuilder.build();
    }
}

