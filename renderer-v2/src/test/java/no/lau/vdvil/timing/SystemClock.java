package no.lau.vdvil.timing;

public class SystemClock implements Clock {
    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}
