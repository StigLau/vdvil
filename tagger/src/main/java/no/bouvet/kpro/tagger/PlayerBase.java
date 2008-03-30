package no.bouvet.kpro.tagger;

import no.bouvet.kpro.renderer.audio.MP3Source;
import java.io.File;
import java.awt.*;

public class PlayerBase implements PlayerIF {

    private boolean started;
    private Worker worker;
    private DynamicTimeTable timeTable;
    private String fileName;

    public PlayerBase(String fileName) throws Exception {
        this.fileName = fileName;

        timeTable = new DynamicTimeTable(this);
        for (int i = 0; i < 10; i++) {
            timeTable.addRow((float) i, (float) i + 1, "Something" + i);
        }
        timeTable.repaintRows();
    }

    public void playPause(Float cue) throws Exception {
        if (!started) {
            worker = new Worker(new MP3Source(new File(fileName)), cue);
            worker.execute();
            started = true;
        } else {
            worker.stop();
            worker = new Worker(new MP3Source(new File(fileName)), cue);


            started = false;

            worker.execute();
            started = true;
        }
    }

    public Component getDynamicTimeTable() {
        return timeTable.getPanel();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
