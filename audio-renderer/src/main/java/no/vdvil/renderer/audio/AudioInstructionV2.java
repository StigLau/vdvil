package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.OldRenderer;
import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.bouvet.kpro.renderer.audio.MP3Source;
import no.lau.vdvil.instruction.SuperInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * V2 Wrapper to create a AudioInstruction
 */
public class AudioInstructionV2 extends SuperInstruction{

    final URL urlInLocalCache;
    final Track track;
    final long cueDifference;
    final long segmentStart;
    final Logger log = LoggerFactory.getLogger(AudioInstructionV2.class);

    public AudioInstructionV2(long start, long length, URL urlInLocalCache, Track track, long cueDifference, long segmentStart) {
        super(start, length);
        this.urlInLocalCache = urlInLocalCache;
        this.track = track;
        this.cueDifference = cueDifference;
        this.segmentStart = segmentStart;
    }

    public AudioInstruction asInstruction(Float masterBpm) {
        if(urlInLocalCache == null)
            throw new RuntimeException(track.mediaFile.fileName + " had not been cached before creating instruction! - Downloader not set");

        Float speedFactor = OldRenderer.RATE * 60 / track.bpm;
        Float differenceBetweenMasterSongAndPart = track.bpm / masterBpm;
        //Start and end come from the composition instructions
        int _start = new Float(start() * speedFactor * differenceBetweenMasterSongAndPart).intValue();
        int _end = new Float((start() + length()) * speedFactor * differenceBetweenMasterSongAndPart).intValue();
        //The cue is where to start inside the mp3 sample
        Float _cue = (segmentStart + cueDifference) * speedFactor + track.mediaFile.startingOffset * OldRenderer.RATE;
        int _duration = _end - _start;

        MP3Source mp3Source = null;
        try {
            mp3Source = new MP3Source(new File(urlInLocalCache.getFile()));
        } catch (IOException e) {
            log.error("Problem accessing MP3 file {}", urlInLocalCache, e);
        }
        AudioInstruction audioInstruction = new AudioInstruction(_start, _end, mp3Source, _cue.intValue(), _duration);

        //Note that Playback speed is a different equation!!
        audioInstruction.setConstantRate(masterBpm / track.bpm);
        return audioInstruction;
    }
}
