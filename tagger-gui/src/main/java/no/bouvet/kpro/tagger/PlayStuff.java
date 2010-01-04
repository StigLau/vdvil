package no.bouvet.kpro.tagger;

import no.bouvet.kpro.renderer.audio.*;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.Part;
import java.io.File;

public class PlayStuff {
    private final Renderer renderer;
    private final Float masterBpm;

    public PlayStuff(Composition composition) throws Exception {
        masterBpm = composition.masterBpm;
        Instructions instructions = createInstructionsFromParts(composition);
        renderer = new Renderer(instructions);
        renderer.addRenderer(new AudioRenderer(new AudioPlaybackTarget()));
    }

    public static Instructions createInstructionsFromParts(Composition composition) throws Exception {
        Instructions instructions = new Instructions();
        for (Part part : composition.parts) {
            AudioSource audioSource = new MP3Source(new File(part.simpleSong.mediaFile.fileName));
            //TODO check why diff neeeds to be opposite
            Float partCompositionDiff = part.bpm / composition.masterBpm;
            Float compositionPartDiff = composition.masterBpm / part.bpm;
            AudioInstruction audioInstruction = new SimpleAudioInstruction(
                    part.startCue,
                    part.endCue,
                    part.bpm,
                    part.segment.start + part.beginAtCue,
                    part.simpleSong.mediaFile.startingOffset,
                    audioSource,
                    partCompositionDiff);
            audioInstruction.setInterpolatedRate(compositionPartDiff, compositionPartDiff);
            instructions.append(audioInstruction);
        }
        return instructions;
    }

    public void play(Float startCue) throws Exception {
        Float startCueInMillis = (startCue * 44100 * 60)/ masterBpm;
        renderer.start(startCueInMillis.intValue());
    }

    public void stop() {
        renderer.stop();
    }
}
