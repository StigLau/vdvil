package no.lau.vdvil.timing;

public class ResolutionTimer {

    private Conductor childTimer;
    int notifyEvery;
    final static int resolution = 1000; // In microseconds
    final Clock clock;
    long timeSlider = 0; //The last found beat, slides along
    final long origo;

    public ResolutionTimer(Clock clock) {
        this(clock, 0);
    }

    /**
     * @param clock the external clock
     * @param origo An offset of where the starting point of this renderer is
     */
    public ResolutionTimer(Clock clock, long origo) {
        this.clock = clock;
        this.timeSlider = origo;
        this.origo = origo;
    }

    public void notifyEvery(Conductor childTimer, int notifyEvery) {
        this.childTimer = childTimer;
        this.notifyEvery = notifyEvery;
    }

    boolean updateSlider(long currentTimeMillis) {
        long foundTime = calculateJump(notifyEvery, currentTimeMillis, timeSlider);

        if(foundTime > timeSlider) {
            timeSlider = foundTime;
            return true;
        } else {
            return false;
        }
    }

    long checkTimeAndNotify() {
        long currentTimeMillis = clock.getCurrentTimeMillis();
        if(updateSlider(currentTimeMillis))
            childTimer.notify(timeSlider - origo);
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

class RunnableResolutionTimer extends ResolutionTimer implements Runnable{

    public RunnableResolutionTimer(Clock clock, long origo) {
        super(clock, origo);
    }

    public void run() {
        while(true) {
            checkTimeAndNotify();
            try {
                //The fine resoultion says how many times the timer should poll to check if the current time has passed.
                // Increasing this number makes triggering more "accurate" At the cost of computation cycles
                int fineResolution = 5;
                Thread.sleep(notifyEvery / fineResolution);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
