package no.lau.vdvil.handler.persistence;

import java.net.URL;

public class DvlXML implements MultimediaReference{
    final String name;
    final URL url;

    public DvlXML(String name, URL url) {
        this.name = name;
        this.url = url;
    }

    public String name() { return name; }
    public URL url() { return url; }
}
