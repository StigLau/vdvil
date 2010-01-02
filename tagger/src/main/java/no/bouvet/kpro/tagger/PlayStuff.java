package no.bouvet.kpro.tagger;

import no.bouvet.kpro.renderer.audio.*;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;
import no.bouvet.kpro.tagger.model.*;

import java.io.File;
import java.util.List;

public class PlayStuff {

    private Instructions instructions = new Instructions();
    private Renderer renderer = null;
    private MasterSong masterSong;

    public void init() throws Exception {
        List<Part> parts = masterSong.parts;
        for (Part part : parts) {
            AudioSource audioSource = new MP3Source(new File(part.simpleSong.fileName));
            Float cue = part.row.cue;

            if(part.beginAtCue != null) {
                cue += part.beginAtCue;
            }
            //TODO check why diff neeeds to be opposite
            Float diffBetweenMasterAndPart = part.bpm / masterSong.masterBpm;
            AudioInstruction audioInstruction = new SimpleAudioInstruction(part.startCue, part.endCue, part.bpm, cue, part.simpleSong.startingOffset, audioSource, diffBetweenMasterAndPart);
            diffBetweenMasterAndPart = masterSong.masterBpm / part.bpm;
            audioInstruction.setInterpolatedRate(diffBetweenMasterAndPart, diffBetweenMasterAndPart);
            instructions.append(audioInstruction);
        }
    }

    public void play(Float startCue) throws Exception {
        Float startCueInMillis = (startCue * 44100 * 60)/ masterSong.masterBpm;
        renderer = new Renderer(instructions);
        AudioTarget target = new AudioPlaybackTarget();
        renderer.addRenderer(new AudioRenderer(target));

        renderer.start(startCueInMillis.intValue());
        /* Not used
        while(renderer.isRendering()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        */
    }

    public void stop() {
        renderer.stop();
    }

    public void setMasterSong(MasterSong masterSong) {
        this.masterSong = masterSong;
    }
}
