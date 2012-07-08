package no.lau.vdvil.handler.persistence;

import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.TimeInterval;
import java.net.URL;

public class PartXML implements CompositionInstruction, MutableCompositionInstruction {
    final String id;
    int start;
    int end;
    int duration;
    int cueDifference; //If the start has been moved, it will affect the cue starting point of multimedia which rely on timing.
    final DvlXML dvl;

    public PartXML(String id, TimeInterval timeInterval, DvlXML dvlXML) {
        this.id = id;
        this.start = timeInterval.start();
        this.duration = timeInterval.duration();
        this.end = timeInterval.start() + timeInterval.duration();
        this.dvl = dvlXML;

        if(start > end)
            throw new IllegalArgumentException("End has to be after start!");
    }

    public String id() { return id; }

    public TimeInterval timeInterval() {
        return new Interval(start, end-start);
    }

    public int start() { return start + cueDifference; }
    public int cueDifference() { return cueDifference; }
    public int duration() {return duration;}
    public int end() { return end; }
    public MultimediaReference dvl() { return dvl; }

    public static CompositionInstruction create(URL url) {
        return new PartXML("Test Part", new Interval(0, 0), new DvlXML("Test DVL", url))  ;
    }

    public void moveStart(int cueDifference) {
        this.cueDifference = cueDifference;
    }

    /**
     * The end beat may sometimes be moved to facilitate an early ending.
     * Really don't like this function which makes the CompositionInstruction mutable.
     * The side effect is that one has to load/parse the original source to get the original state
     * Should be a way of creating a copy of the original structure without mutating!
     * @param endBeat set endBeat
     */
    public void setEnd(int endBeat) {
        this.end = endBeat;
    }

    public String toString() {
        return start + " - " + end + " " + id;
    }
}
