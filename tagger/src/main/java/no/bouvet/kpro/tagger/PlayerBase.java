package no.bouvet.kpro.tagger;

import no.bouvet.kpro.tagger.model.SimpleSong;
import no.bouvet.kpro.tagger.gui.DynamicTimeTable;
import no.bouvet.kpro.tagger.gui.Worker;

import java.awt.*;

public class PlayerBase implements PlayerIF {

    private boolean started;
    private Worker worker;
    private DynamicTimeTable timeTable;
    private SimpleSong simpleSong;

    public PlayerBase(SimpleSong simpleSong) throws Exception {
        this.simpleSong = simpleSong;
        timeTable = new DynamicTimeTable(this, simpleSong);
    }

    public void playPause(Float cue) throws Exception {
        if (!started) {
            worker = new Worker(simpleSong, cue);
            worker.execute();
            started = true;
        } else {
            worker.stop();
            worker = new Worker(simpleSong, cue);


            started = false;

            worker.execute();
            started = true;
        }
    }

    public Component getDynamicTimeTable() {
        return timeTable.getPanel();
    }
}