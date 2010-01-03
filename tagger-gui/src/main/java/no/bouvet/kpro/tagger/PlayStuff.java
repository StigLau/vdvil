package no.bouvet.kpro.tagger;

import no.bouvet.kpro.renderer.audio.*;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.Part;

import java.io.File;
import java.util.List;

public class PlayStuff {

    private Instructions instructions = new Instructions();
    private Renderer renderer = null;
    private Composition composition;

    public void init() throws Exception {
        List<Part> parts = composition.parts;
        for (Part part : parts) {
            AudioSource audioSource = new MP3Source(new File(part.simpleSong.mediaFile.fileName));
            Float cue = part.segment.start;

            if(part.beginAtCue != null) {
                cue += part.beginAtCue;
            }
            //TODO check why diff neeeds to be opposite
            Float diffBetweenMasterAndPart = part.bpm / composition.masterBpm;
            AudioInstruction audioInstruction = new SimpleAudioInstruction(part.startCue, part.endCue, part.bpm, cue, part.simpleSong.mediaFile.startingOffset, audioSource, diffBetweenMasterAndPart);
            diffBetweenMasterAndPart = composition.masterBpm / part.bpm;
            audioInstruction.setInterpolatedRate(diffBetweenMasterAndPart, diffBetweenMasterAndPart);
            instructions.append(audioInstruction);
        }
    }

    public void play(Float startCue) throws Exception {
        Float startCueInMillis = (startCue * 44100 * 60)/ composition.masterBpm;
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

    public void setMasterSong(Composition composition) {
        this.composition = composition;
    }
}
