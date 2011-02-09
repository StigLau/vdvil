package no.lau.tagger.model;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.audio.AudioSource;
import no.bouvet.kpro.renderer.audio.MP3Source;
import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.bouvet.kpro.renderer.audio.SimpleAudioInstruction;
import no.lau.vdvil.cache.VdvilCache;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import java.io.File;
import java.io.IOException;

public class AudioPart extends AbstractPart{
    VdvilCache cache = VdvilHttpCache.create();
    public final SimpleSong simpleSong;
    public final Segment segment;
    public final Float bpm;

    public final int beginAtCue;

    /*
     * Simple constructor for most usages
     */
    public AudioPart(SimpleSong simpleSong, int startCue, int endCue, Segment segment) {
        this(simpleSong, segment, simpleSong.bpm, startCue, endCue, 0);
    }

    public AudioPart(SimpleSong simpleSong, Segment segment, Float bpm, int startCue, int endCue, int beginAtCue) {
        super(startCue, endCue);
        this.simpleSong = simpleSong;
        this.segment = segment;
        this.bpm = bpm;
        this.beginAtCue = beginAtCue;
    }

    public Instruction translateToInstruction(Float masterBpm) throws IOException {
        //TODO check why diff neeeds to be opposite        
        Float partCompositionDiff = bpm / masterBpm;
        Float compositionPartDiff = masterBpm / bpm;
        File mp3File = cache.fileLocation(simpleSong.mediaFile.fileName);
        AudioSource audioSource = new MP3Source(mp3File);

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