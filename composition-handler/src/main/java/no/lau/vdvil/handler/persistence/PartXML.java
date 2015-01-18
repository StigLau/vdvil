package no.lau.vdvil.handler.persistence;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.TimeInterval;

public class PartXML implements CompositionInstruction, MutableCompositionInstruction {
    final String id;
    int start;

    Integer duration;
    int cueDifference; //If the start has been moved, it will affect the cue starting point of multimedia which rely on timing.
    public final DvlXML dvl;

    public PartXML(String id, TimeInterval timeInterval, DvlXML dvlXML) {
        this.id = id;
        this.start = timeInterval.start();
        this.duration = timeInterval.duration();
        this.dvl = dvlXML;

        if(duration() < 0)
            throw new IllegalArgumentException("Must have a positive duration!");
    }

    public String id() { return id; }

    public TimeInterval timeInterval() {
        return new Interval(start, duration());
    }

    public int start() { return start + cueDifference; }
    public int cueDifference() { return cueDifference; }
    public int duration() {
        return duration;
    }

    /**
     * Shorthand for calculating end
     */
    public int end() {
        return start() + duration;
    }
    public MultimediaReference dvl() { return dvl; }

    public static CompositionInstruction create(FileRepresentation fileRepresentation) {
        return new PartXML("Test Part", new Interval(0, 0), new DvlXML(fileRepresentation))  ;
    }

    public void setCueDifference(int cueDifference) {
        this.cueDifference = cueDifference;
    }

    /**
     * The duration may sometimes be moved to facilitate an early ending.
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String toString() {
        return start() + " + " + duration() + " " + id;
    }
}
