package no.lau.tagger.model;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.audio.AudioSource;
import no.bouvet.kpro.renderer.audio.MP3Source;
import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.bouvet.kpro.renderer.audio.SimpleAudioInstruction;
import java.util.List;
import java.util.Collections;
import java.io.File;

public class Part implements IPart {
    public final SimpleSong simpleSong;
    public final Segment segment;

    public final Float bpm;
    public final Float startCue;
    public final Float endCue;

    public final Float beginAtCue;

    /**
     * Simple constructor for most usages
     */
    public Part(SimpleSong simpleSong, Float startCue, Float endCue, Segment segment) {
        this.simpleSong = simpleSong;
        this.segment = segment;
        this.bpm = simpleSong.bpm;
        this.startCue = startCue;
        this.endCue = endCue;
        this.beginAtCue = 0F;
    }

    public Part(SimpleSong simpleSong, Segment segment, Float bpm, Float startCue, Float endCue, Float beginAtCue) {
        this.simpleSong = simpleSong;
        this.segment = segment;
        this.bpm = bpm;
        this.startCue = startCue;
        this.endCue = endCue;
        this.beginAtCue = beginAtCue;
    }

    public List<IPart> children() {
        return Collections.emptyList();
    }

    public Instruction translateToInstruction(Float masterBpm) throws Exception {
        //TODO check why diff neeeds to be opposite        
        Float partCompositionDiff = bpm / masterBpm;
        Float compositionPartDiff = masterBpm / bpm;

        AudioSource audioSource = new MP3Source(new File(simpleSong.mediaFile.fileName));
        AudioInstruction audioInstruction = new SimpleAudioInstruction(
                startCue,
                endCue,
                bpm,
                segment.start + beginAtCue,
                simpleSong.mediaFile.startingOffset,
                audioSource,
                partCompositionDiff);
        audioInstruction.setInterpolatedRate(compositionPartDiff, compositionPartDiff);
        return audioInstruction;
    }
}