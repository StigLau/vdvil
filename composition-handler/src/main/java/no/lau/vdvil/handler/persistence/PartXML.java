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
}
