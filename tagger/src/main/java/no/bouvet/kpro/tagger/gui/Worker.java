package no.bouvet.kpro.tagger.gui;

import no.bouvet.kpro.renderer.audio.AudioSource;
import no.bouvet.kpro.renderer.audio.SimpleAudioInstruction;
import no.bouvet.kpro.renderer.audio.MP3Source;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.tagger.model.SimpleSong;
import no.bouvet.kpro.tagger.model.MasterSong;
import no.bouvet.kpro.tagger.AudioPlayer;
import no.bouvet.kpro.tagger.PlayStuff;
import no.bouvet.kpro.tagger.PlayingStuffDemo;

import javax.swing.*;
import java.util.List;
import java.io.File;

public class Worker extends SwingWorker<Object, Object> {


    AudioSource audioSource;
    private MasterSong masterSong;
    Float cue;
    private Float bpm;
    private PlayStuff playStuff;

    public Worker(MasterSong masterSong, Float cueToStartOn, Float bpm) {
        this.masterSong = masterSong;
        this.cue = cueToStartOn;
        this.bpm = bpm;
    }

    protected Object doInBackground() throws Exception {
        /*
        This is how it should be done
        PlayingStuffDemo test = new PlayingStuffDemo();
        test.beforeMethod();
        try {
            test.testPlayingSomeStuff();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        playStuff = new PlayStuff();
        playStuff.setMasterSong(masterSong);
        playStuff.init();
        playStuff.setBpm(bpm);
        playStuff.play(cue);
        return "worker finished";
    }

    protected void process(List<Object> objects) {
        super.process(objects);    //To change body of overridden methods use File | Settings | File Templates.
        System.out.println("Here is the intermediate result = " + objects);
    }

    protected void done() {
        System.out.println("Player instance done");
        super.done();
    }

    public void stop() {
        playStuff.stop();
    }
}
