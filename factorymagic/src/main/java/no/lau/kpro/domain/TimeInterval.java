package no.lau.kpro.domain;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jun 22, 2008
 */
public class TimeInterval {
    private int start;
    private int end;

    public TimeInterval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public boolean containsInterval(TimeInterval other) {
        return (start < other.end && end > other.start);
    }
}
