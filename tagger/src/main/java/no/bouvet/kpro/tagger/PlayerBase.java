package no.bouvet.kpro.tagger;

import no.bouvet.kpro.tagger.model.SimpleSong;
import no.bouvet.kpro.tagger.model.MasterSong;
import no.bouvet.kpro.tagger.model.Row;
import no.bouvet.kpro.tagger.model.Part;
import no.bouvet.kpro.tagger.gui.DynamicTimeTable;
import no.bouvet.kpro.tagger.gui.Worker;

import java.awt.*;

public class PlayerBase implements PlayerIF {

    private DynamicTimeTable timeTable;
    private MasterSong masterSong;
    private Float bpm;
    private Worker worker;

    public PlayerBase(SimpleSong simpleSong) throws Exception {
        System.out.println("PlayerBase");
        masterSong = new MasterSong();
        bpm = simpleSong.bpm;

        for (Row row : simpleSong.rows) {
            Part part = new Part();
            part.setRow(row);
            part.setSimpleSong(simpleSong);
            part.setStartCue(row.cue);
            part.setEndCue(row.end);
            part.setBpm(simpleSong.bpm);
            masterSong.addPart(part);
        }
        timeTable = new DynamicTimeTable(this, simpleSong);
    }

    public void playPause(Float cue) throws Exception {
        if(worker != null) {
            worker.stop();
            worker = null;
        }
        worker = new Worker(masterSong, cue, bpm);
        worker.execute();
    }

    public Component getDynamicTimeTable() {
        return timeTable.getPanel();
    }
}
