package no.lau.vdvil.timingframework;

import no.lau.vdvil.player.VdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.jdesktop.core.animation.timing.*;
import org.jdesktop.swing.animation.timing.sources.SwingTimerTimingSource;

import java.util.concurrent.TimeUnit;

public class TimingFrameworkMasterRenderer implements VdvilPlayer {

    final Animator animator;
    final TimingSource scheduler;

    public TimingFrameworkMasterRenderer(MasterBeatPattern masterBeatPattern, TimingTarget... timingTargets) {
        //scheduler = new ScheduledExecutorTimingSource(200, TimeUnit.MILLISECONDS);
        scheduler = new SwingTimerTimingSource(200, TimeUnit.MILLISECONDS);
        animator = createAnimator(masterBeatPattern, timingTargets);
    }

    public void play() {
        scheduler.init();
        animator.start();
    }

    public void stop() {
        animator.cancel();
    }

    public boolean isPlaying() {
        return animator.isRunning();
    }

    private Animator createAnimator(MasterBeatPattern masterBeatPattern, TimingTarget... timingTargets) {
        Animator.Builder animatorBuilder = new Animator.Builder(scheduler)
                .setDuration(masterBeatPattern.durationCalculation().longValue(), TimeUnit.MILLISECONDS);
        for (TimingTarget timingTarget : timingTargets) {
            animatorBuilder.addTarget(timingTarget);
        }
        return animatorBuilder.build();
    }
}

