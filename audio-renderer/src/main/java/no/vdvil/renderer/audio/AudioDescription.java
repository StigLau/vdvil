package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.bouvet.kpro.renderer.audio.MP3Source;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AudioDescription implements MultimediaPart {

    private Segment segment;
    private CompositionInstruction compositionInstruction;
    final Track track;
    private URL urlInLocalCache;

    public AudioDescription(Segment segment, CompositionInstruction compositionInstruction, Track track, URL urlInLocalCache) {
        this.segment = segment;
        this.compositionInstruction = compositionInstruction;
        this.track = track;
        this.urlInLocalCache = urlInLocalCache;
    }

    public AudioInstruction asInstruction(Float masterBpm) throws IOException{
        Float speedFactor = 44100 * 60 / track.bpm;
        Float differenceBetweenMasterSongAndPart = track.bpm / masterBpm;
        //Start and end come from the composition instructions
        int _start = new Float(compositionInstruction.start() * speedFactor * differenceBetweenMasterSongAndPart).intValue();
        int _end = new Float(compositionInstruction.end() * speedFactor * differenceBetweenMasterSongAndPart).intValue();
        //The cue is where to start inside the mp3 sample
        Float _cue = segment.start + track.mediaFile.startingOffset * 44100;
        int _duration = _end - _start;

        MP3Source mp3Source = new MP3Source(new File(urlInLocalCache.getFile()));
        AudioInstruction audioInstruction = new AudioInstruction(_start, _end, mp3Source, _cue.intValue(), _duration);

        //Note that Playback speed is a different equation!!
        audioInstruction.setConstantRate(masterBpm / track.bpm);
        return audioInstruction;
    }
}
