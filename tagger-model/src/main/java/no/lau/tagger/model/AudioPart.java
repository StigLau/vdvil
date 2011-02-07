package no.lau.tagger.model;

import no.lau.vdvil.common.VdvilFileCache;
import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.audio.AudioSource;
import no.bouvet.kpro.renderer.audio.MP3Source;
import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.bouvet.kpro.renderer.audio.SimpleAudioInstruction;
import java.io.IOException;

public class AudioPart extends AbstractPart{
    public final SimpleSong simpleSong;
    public final Segment segment;
    public final Float bpm;

    public final Float beginAtCue;

    private VdvilFileCache cache = null;

    /*
     * Simple constructor for most usages
     */
    public AudioPart(SimpleSong simpleSong, Float startCue, Float endCue, Segment segment) {
        this(simpleSong, segment, simpleSong.bpm, startCue, endCue, 0F);
    }

    public AudioPart(SimpleSong simpleSong, Segment segment, Float bpm, Float startCue, Float endCue, Float beginAtCue) {
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

        String url = simpleSong.mediaFile.fileName;
        String checksum = simpleSong.mediaFile.checksum;
        //Download the file in case is it is not located in cache
        if(!cache.existsInRepository(url, checksum)) {
            cache.fetchAsStream(url, checksum);
        }
        AudioSource audioSource = new MP3Source(cache.fetchFromRepository(url));

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

    public void setCache(VdvilFileCache cache) {
        this.cache = cache;
    }
}