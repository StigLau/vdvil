package no.lau.vdvil.handler.persistence;

import no.lau.vdvil.cache.FileRepresentation;
import java.net.URL;

public class DvlXML implements MultimediaReference{
    final String name;
    final URL url;
    final String fileChecksum;

    @Deprecated //Plz use with checksum!
    public DvlXML(String name, URL url) {
        this.name = name;
        this.url = url;
        this.fileChecksum = null;
    }

    public DvlXML(FileRepresentation fileRepresentation) {
        this.name = null;
        this.url = fileRepresentation.remoteAddress();
        this.fileChecksum = fileRepresentation.md5CheckSum();
    }

    public String name() { return name; }
    public URL url() { return url; }
    public String fileChecksum() { return fileChecksum;}
}
