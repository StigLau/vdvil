package no.bouvet.kpro.tagger;

import no.bouvet.kpro.tagger.gui.SimpleSongCallBack;
import no.lau.tagger.model.SimpleSong;
import no.bouvet.kpro.tagger.gui.DynamicTimeTable;
import no.bouvet.kpro.tagger.gui.Worker;

import javax.swing.*;
import java.awt.Component;

public class PlayerBase implements PlayerIF, SimpleSongCallBack {

    private boolean started;
    private Worker worker;
    private DynamicTimeTable timeTable;
    private SimpleSong simpleSong;

    public static final PlayerBase NULL = new PlayerBase(SimpleSong.NULL); 

    public PlayerBase(SimpleSong simpleSong) {
        this.simpleSong = simpleSong;
        timeTable = new DynamicTimeTable(this, simpleSong, this);
    }

    public void playPause(Float startCue, Float endCue) throws Exception {
        if (!started) {
            worker = new Worker(simpleSong, startCue, endCue);
            worker.execute();
            started = true;
        } else {
            worker.stop();
            worker = new Worker(simpleSong, startCue, endCue);


            started = false;

            worker.execute();
            started = true;
        }
    }

    public JPanel getDynamicTimeTable() {
        return timeTable.getPanel();
    }

    public SimpleSong getSimpleSong() {
        return simpleSong;
    }

    public void update(SimpleSong changedSimpleSong) {
        this.simpleSong = changedSimpleSong;
    }
}
