package no.bouvet.kpro.tagger;

import no.bouvet.kpro.renderer.audio.*;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;
import no.bouvet.kpro.tagger.model.Part;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class PlayStuff {

    Instructions instructions = new Instructions();
    List<Part> parts = new ArrayList<Part>();
    Renderer renderer = null;
    Float bpm;

    public void init() throws Exception {

        for (Part part : parts) {
            AudioSource audioSource = new MP3Source(new File(part.getSimpleSong().fileName));
            Float cue = part.getRow().cue;
            int start = part.getStartCue().intValue();
            int end = part.getEndCue().intValue();

            float bpm = part.getBpm();
            instructions.append(new SimpleAudioInstruction(start, end, bpm, cue, part.getSimpleSong().startingOffset, audioSource));
        }
    }

    public void play(int startTime) throws Exception {
        Float startCueInMillis = (startTime * 44100 * 60)/ bpm;

        renderer = new Renderer(instructions);
        AudioTarget target = new AudioPlaybackTarget();
        renderer.addRenderer(new AudioRenderer(target));

        renderer.start(startCueInMillis.intValue());

        while(renderer.isRendering()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
}
