package no.bouvet.kpro.tagger;

import no.bouvet.kpro.renderer.audio.AudioSource;
import no.bouvet.kpro.renderer.audio.SimpleAudioInstruction;
import no.bouvet.kpro.renderer.Instructions;

import javax.swing.*;

public class Worker extends SwingWorker<Object, Object> {


    AudioSource audioSource;
    Float cue;
    private AudioPlayer player;

    protected Object doInBackground() throws Exception {
        Float coronaOffset = 44100 * 0.445f;
        Instructions instructions = new Instructions();
        instructions.append(new SimpleAudioInstruction(0, 256, 132.98f, cue, coronaOffset, audioSource));
        player = new AudioPlayer();
        player.playMusic(instructions);
        return "Somethings finished";
    }

    protected void done() {
        System.out.println("Done");
        super.done();
    }

    public void stop() {
        player.stop();
        audioSource.close();
    }
}
