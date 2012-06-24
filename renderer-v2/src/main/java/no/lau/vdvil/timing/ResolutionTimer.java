package no.lau.vdvil.timing;

public class ResolutionTimer {

    private BeatTimeConverter childTimer;
    int notifyEvery;
    final static int resolution = 1000; // In microseconds
    final Clock clock;
    long timeSlider; //Next beat to find, slides along
    final long origo;

    /**
     * @param clock the external clock
     * @param origo An offset of where the starting point of this renderer is
     */
    public ResolutionTimer(Clock clock, long origo) {
        this.clock = clock;
        this.timeSlider = origo;
        this.origo = origo;
    }

    public void notifyEvery(BeatTimeConverter childTimer, int notifyEvery) {
        this.childTimer = childTimer;
        this.notifyEvery = notifyEvery;
    }

    /**
     * Returns the found time. Null if the slider wasn't moved.
     */
    Long updateSlider(long currentTimeMillis) {
        long nextSlide = calculateJump(notifyEvery, currentTimeMillis, timeSlider);

        if(currentTimeMillis >= timeSlider && nextSlide >= timeSlider) {
            Long previousTime = timeSlider;
            timeSlider = timeSlider + notifyEvery;
            return previousTime;
        } else {
            return null;
        }
    }

    long checkTimeAndNotify() {
        long currentTimeMillis = clock.getCurrentTimeMillis();
        Long foundTime = updateSlider(currentTimeMillis);
        if (foundTime != null) {
            childTimer.notify(foundTime - origo);
            return foundTime;
        } else
            return timeSlider;
    }

    /**
     * Calculate which time jump ahead matches the jumpAhead without going over the current time
     */
    long calculateJump(int notifyEvery, long currentTimeMillis, long lastFoundBeat) {
        long jumps = (currentTimeMillis - lastFoundBeat) / notifyEvery;
        return lastFoundBeat + notifyEvery * jumps;
    }

    public int resolution() {
        return resolution;
    }
}

