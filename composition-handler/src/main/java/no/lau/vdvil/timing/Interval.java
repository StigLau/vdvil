package no.lau.vdvil.timing;

/**
 * Simple implementation of TimeInterval for keeping track of time
 */
public class Interval implements TimeInterval{
    final Integer start;
    final Integer duration;

    public Interval(Integer start, Integer duration) {
        this.start = start;
        this.duration = duration;
    }

    public Integer start() {
        return start;
    }

    public Integer duration() {
        return duration;
    }
}
