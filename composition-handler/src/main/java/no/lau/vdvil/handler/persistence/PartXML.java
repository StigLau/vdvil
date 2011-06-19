package no.lau.vdvil.handler.persistence;

import java.net.URL;

public class PartXML implements CompositionInstruction {
    String id;
    int start;
    int end;
    DvlXML dvl;

    public String id() { return id; }
    public int start() { return start; }
    public int end() { return end; }
    public MultimediaReference dvl() { return dvl; }

    public static CompositionInstruction create(String id, int start, int end, MultimediaReference dvl) {
        PartXML partXML = new PartXML();
        partXML.id = id;
        partXML.start = start;
        partXML.end = end;
        partXML.dvl = (DvlXML) dvl;
        return partXML;
    }

    public static CompositionInstruction create(URL url) {
        return create("Test Part", -1, -1, DvlXML.create("Test DVL", url));
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
}
