package no.bouvet.kpro.tagger.persistence;

import no.bouvet.kpro.tagger.model.SimpleSong;

import java.io.*;

public class SimpleSongParser {

    public void save(SimpleSong song, String fileToSave) {
        File folder = new File("/tmp/kpro");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File("/tmp/kpro" + fileToSave);


        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(song);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();  
        }

    }

    public SimpleSong load(String fileName) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("/tmp/kpro" + fileName));
            return (SimpleSong) ois.readObject ();
        } catch(IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close ();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}