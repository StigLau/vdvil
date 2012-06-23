package no.lau.vdvil.timing;

/**
 * Special Renderer for handling timing interpretations
 */
public class BeatTimeConverter {

    private final Conductor renderer;
    private final ResolutionTimer timer;
    private final int speed;
    private final int perMinute;

    public BeatTimeConverter(Conductor renderer, ResolutionTimer timer, int speed, int perMinute) {
        this.renderer = renderer;
        this.speed = speed;
        this.perMinute = perMinute;
        this.timer = timer;
        timer.notifyEvery(this, calculateResolution(speed, perMinute));
    }

    public void notify(long time) {
        renderer.notify(convertToBeat(time));
    }

    private int convertToBeat(long time) {
        return (int) (time  * speed / (perMinute * timer.resolution()));
    }

    int calculateResolution(int speed, int perMinute) {
        return ResolutionTimer.resolution * perMinute / speed;
    }
}
