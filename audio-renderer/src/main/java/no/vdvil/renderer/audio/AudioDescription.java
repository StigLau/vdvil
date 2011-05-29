package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.bouvet.kpro.renderer.audio.MP3Source;
import no.lau.vdvil.handler.MultimediaPart;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AudioDescription implements MultimediaPart {

    private Segment segment;
    final Track track;
    final URL dvlSrc;
    private URL urlInLocalCache;

    public AudioDescription(Segment segment, Track track, URL dvlSrc, URL urlInLocalCache) {
        this.segment = segment;
        this.track = track;
        this.dvlSrc = dvlSrc;
        this.urlInLocalCache = urlInLocalCache;
    }

    public AudioInstruction asInstruction(int cue, int end, Float masterBpm) throws IOException{
        Float speedFactor = 44100 * 60 / track.bpm;
        Float differenceBetweenMasterSongAndPart = track.bpm / masterBpm;

        int _start = new Float(segment.start * speedFactor * differenceBetweenMasterSongAndPart).intValue();
        int _end = new Float(segment.end * speedFactor * differenceBetweenMasterSongAndPart).intValue();
        int _cue = new Float((cue * speedFactor) + (track.mediaFile.startingOffset * 44100)).intValue();
        int _duration = _end - _start;

        MP3Source mp3Source = new MP3Source(new File(urlInLocalCache.getFile()));
        return new AudioInstruction(_start, _end, mp3Source, _cue, _duration);
        //TODO NOT FINISHED WITH creating AudioInstruction correctly
    }
}
