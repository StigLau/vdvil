package no.bouvet.kpro.tagger.persistence;

import no.bouvet.kpro.tagger.model.SimpleSong;
import no.bouvet.kpro.tagger.model.Row;
import com.thoughtworks.xstream.XStream;

import java.io.*;

public class XStreamParser <T> {

    public static final String path = System.getProperty("user.home") + "/kpro";

    XStream xstream = new XStream();

    public XStreamParser() {
        xstream.alias("song", SimpleSong.class);
        xstream.alias("row", Row.class);
    }

    public <T> void save(T songToSave, String fileToSave) {
        String xml = xstream.toXML(songToSave);
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try {
            BufferedWriter outputStream = new BufferedWriter(new FileWriter(fileToSave));
            outputStream.write(xml);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> T load(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            return (T) xstream.fromXML(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
