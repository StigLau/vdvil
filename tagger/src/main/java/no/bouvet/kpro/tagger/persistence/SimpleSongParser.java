package no.bouvet.kpro.tagger.persistence;

import no.bouvet.kpro.tagger.model.SimpleSong;

import java.io.*;

public class SimpleSongParser {

    public static final String path = "/tmp/kpro";

    public void save(SimpleSong song, String fileToSave) {
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(fileToSave);
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(song);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SimpleSong load(String fileName) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            SimpleSong simpleSong = (SimpleSong) ois.readObject();
            ois.close();
            return simpleSong;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}