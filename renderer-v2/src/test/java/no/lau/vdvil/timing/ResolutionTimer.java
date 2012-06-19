package no.lau.vdvil.timing;

import no.lau.vdvil.renderer.Renderer;

public class ResolutionTimer {

    private Renderer childTimer;
    private int notifyEvery;
    final static int resolution = 1000; // In microseconds
    private final Clock clock;
    long lastBeat = 0;


    public ResolutionTimer(Clock clock) {
        this(clock, 0);
    }

    /**
     *
     * @param clock
     * @param origo An offset of where the starting point of this renderer is
     */
    public ResolutionTimer(Clock clock, long origo) {
        this.clock = clock;
        this.lastBeat = origo;
    }

    public void notifyEvery(Renderer childTimer, int notifyEvery) {
        this.childTimer = childTimer;
        this.notifyEvery = notifyEvery;
    }

    long checkBeat() {
        long lastTime = checkBeat(lastBeat);
        if(lastTime >= lastBeat) {
            lastBeat = lastTime;
            childTimer.notify(lastBeat);
        }
        return lastTime;
    }
    long checkBeat(long lastFoundBeat) {
        long currentTime = clock.getCurrentTimeMillis();
        if(lastFoundBeat + notifyEvery <= currentTime) {
            return checkBeat(lastFoundBeat + notifyEvery);
        } else
            return lastFoundBeat;
    }

    public int resolution() {
        return resolution;
    }
}

class RunnableResolutionTimer extends ResolutionTimer implements Runnable{

    public RunnableResolutionTimer(Clock clock, long origo) {
        super(clock, origo);
    }

    public void run() {
        checkBeat();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
