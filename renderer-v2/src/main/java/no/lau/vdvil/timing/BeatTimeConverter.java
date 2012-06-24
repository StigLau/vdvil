package no.lau.vdvil.timing;

import no.lau.vdvil.control.Conductor;

/**
 * Special OldRenderer for handling timing interpretations
 */
public class BeatTimeConverter {

    private final Conductor renderer;
    private final ResolutionTimer timer;
    private final int speed;
    private final int beatsInAMinute = 60;

    public BeatTimeConverter(Conductor renderer, ResolutionTimer timer, int speed) {
        this.renderer = renderer;
        this.speed = speed;
        this.timer = timer;
        timer.notifyEvery(this, calculateResolution(speed));
    }

    public void notify(long time) {
        renderer.notify(convertToBeat(time));
    }

    private int convertToBeat(long time) {
        return (int) (time  * speed / (beatsInAMinute * timer.resolution()));
    }

    int calculateResolution(int speed) {
        return ResolutionTimer.resolution * beatsInAMinute / speed;
    }

    /**
     * @return the current time position as beat
     */
    public long getCurrentBeat() {
        return convertToBeat(timer.timeSlider - timer.origo);
    }
}
