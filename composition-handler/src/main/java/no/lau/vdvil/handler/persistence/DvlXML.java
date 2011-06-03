package no.lau.vdvil.handler.persistence;

import java.net.URL;

public class DvlXML implements MultimediaReference{
    String name;
    URL url;

    public String name() { return name; }
    public URL url() { return url; }

    public static MultimediaReference create(String name, URL url) {
        DvlXML dvlXML = new DvlXML();
        dvlXML.name = name;
        dvlXML.url = url;
        return dvlXML;
    }
}
