package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.bouvet.kpro.renderer.audio.MP3Source;
import no.lau.vdvil.handler.MultimediaPart;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AudioDescription implements MultimediaPart {

    final Track track;
    final URL dvlSrc;
    private URL urlInLocalCache;

    public AudioDescription(Track track, URL dvlSrc, URL urlInLocalCache) {
        this.track = track;
        this.dvlSrc = dvlSrc;
        this.urlInLocalCache = urlInLocalCache;
    }
    public AudioInstruction asInstruction(String id, int start, int end, int cue, Float bpm, Float masterBpm) throws IOException{
        for (Segment segment : track.segments) {
            if(id.equals(segment.id)) {
                Float speedFactor = 44100 * 60 / bpm;
                Float differenceBetweenMasterSongAndPart = bpm / masterBpm;

                int _start = new Float(start * speedFactor * differenceBetweenMasterSongAndPart).intValue();
                int _end = new Float(end * speedFactor * differenceBetweenMasterSongAndPart).intValue();
                int _cue = new Float((cue * speedFactor) + (track.mediaFile.startingOffset * 44100)).intValue();
                int _duration = _end - _start;

                MP3Source mp3Source = new MP3Source(new File(urlInLocalCache.getFile()));
                return new AudioInstruction(_start, _end, mp3Source, _cue, _duration);
                //TODO NOT FINISHED WITH creating AudioInstruction correctly
            }
        }
        //return new AudioInstruction(start, end, bpm, dvlSrc);
        throw new RuntimeException("Not implemented yet");
    }
}
