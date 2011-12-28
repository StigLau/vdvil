package no.lau.vdvil.timing;

/**
 * Carrier of the current time. This is the played back time - cannot be compared to Currenttimemillis, as real time can progress faster or slower.
 */
public class Time {

    private int timeValue;

    public Time(int time) {
        this.timeValue = time;
    }

    public int asInt() {
        return timeValue;
    }
}
