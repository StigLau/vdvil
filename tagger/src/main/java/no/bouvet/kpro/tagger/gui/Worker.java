package no.bouvet.kpro.tagger.gui;

import no.bouvet.kpro.renderer.audio.AudioSource;
import no.bouvet.kpro.renderer.audio.SimpleAudioInstruction;
import no.bouvet.kpro.renderer.audio.MP3Source;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.tagger.model.SimpleSong;
import no.bouvet.kpro.tagger.AudioPlayer;

import javax.swing.*;
import java.util.List;
import java.io.File;

public class Worker extends SwingWorker<Object, Object> {


    AudioSource audioSource;
    private SimpleSong simpleSong;
    Float startCue;
    private Float endCue;
    private AudioPlayer player;

    public Worker(SimpleSong simpleSong, Float startCue, Float endCue) {
        this.simpleSong = simpleSong;
        this.startCue = startCue;
        this.endCue = endCue;
        try {
            audioSource = new MP3Source(new File(simpleSong.fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Object doInBackground() throws Exception {
        Instructions instructions = new Instructions();
        System.out.println("startCue playing = " + startCue);
        Float playLength = endCue - startCue;
        instructions.append(new SimpleAudioInstruction(0F, playLength, simpleSong.bpm, startCue, simpleSong.startingOffset, audioSource, 1F));
        player = new AudioPlayer();
        player.playMusic(instructions);
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
        player.stop();
        audioSource.close();
    }
}
