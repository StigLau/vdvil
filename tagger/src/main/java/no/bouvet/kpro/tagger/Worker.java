package no.bouvet.kpro.tagger;

import no.bouvet.kpro.renderer.audio.AudioSource;
import no.bouvet.kpro.renderer.audio.SimpleAudioInstruction;
import no.bouvet.kpro.renderer.Instructions;

import javax.swing.*;
import java.util.List;

public class Worker extends SwingWorker<Object, Object> {


    AudioSource audioSource;
    Float cue;
    private AudioPlayer player;

    public Worker(AudioSource audioSource, Float cue) {
        this.audioSource = audioSource;
        this.cue = cue;
    }

    protected Object doInBackground() throws Exception {
        Float coronaOffset = 44100 * 0.445f;
        Instructions instructions = new Instructions();
        System.out.println("cue playing = " + cue);
        instructions.append(new SimpleAudioInstruction(0, 256, 132.98f, cue, coronaOffset, audioSource));
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
