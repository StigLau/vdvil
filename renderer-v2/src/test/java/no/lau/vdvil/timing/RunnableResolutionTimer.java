package no.lau.vdvil.timing;

public class RunnableResolutionTimer extends ResolutionTimer implements Runnable{

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

    public void play() {
        new Thread(this).start();
    }
}
