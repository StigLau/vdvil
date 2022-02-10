package no.lau.vdvil.cache;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public interface FileRepresentation {
    URL remoteAddress();
    File localStorage(); //Confirmed stored locally
    String md5CheckSum();
    int downloadAttemptsLeft();

    //Used in tests and when there are no uses for a FileRepresentation (Metronome Renderer)
    FileRepresentation NULL = new FileRepresentation() {
        public URL remoteAddress() {return nullURL();}
        public File localStorage() {return new File("NULL");}
        public String md5CheckSum() {return "NULL";}
        public int downloadAttemptsLeft() { return 0; }
        public String toString() { return "NULL FileRepresentation";}

        private URL nullURL(){
            try { return new URL("NULL");
            } catch (MalformedURLException e) {throw new RuntimeException();}
        }
    };
}
