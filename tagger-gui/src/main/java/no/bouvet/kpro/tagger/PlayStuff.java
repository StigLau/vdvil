package no.bouvet.kpro.tagger;

import no.bouvet.kpro.renderer.*;
import no.bouvet.kpro.renderer.audio.*;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.LyricPart;
import no.lau.tagger.model.Part;
import java.io.File;

/**
 * This is the master class, responsible for playing a small demoset of VDVIL music
 */
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
            //TODO check why diff neeeds to be opposite
            Float partCompositionDiff = part.bpm / composition.masterBpm;
            Float compositionPartDiff = composition.masterBpm / part.bpm;

            Instruction instruction;
            if(part instanceof LyricPart)
                instruction = createLyricInstruction((LyricPart) part);
            else
                instruction = createAudioInstruction(part, partCompositionDiff, compositionPartDiff);
            instructions.append(instruction);
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

    private static AudioInstruction createAudioInstruction(Part part, Float partCompositionDiff, Float compositionPartDiff) throws Exception {
        AudioSource audioSource = new MP3Source(new File(part.simpleSong.mediaFile.fileName));
        AudioInstruction audioInstruction = new SimpleAudioInstruction(
                part.startCue,
                part.endCue,
                part.bpm,
                part.segment.start + part.beginAtCue,
                part.simpleSong.mediaFile.startingOffset,
                audioSource,
                partCompositionDiff);
        audioInstruction.setInterpolatedRate(compositionPartDiff, compositionPartDiff);
        return audioInstruction;
    }

    private static Instruction createLyricInstruction(LyricPart lyricPart) {
        return new SimpleLyricInstruction(
                lyricPart.startCue,
                lyricPart.endCue,
                lyricPart.bpm,
                lyricPart.text
        );
    }
}
