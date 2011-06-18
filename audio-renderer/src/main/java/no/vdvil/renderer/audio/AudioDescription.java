package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.bouvet.kpro.renderer.audio.MP3Source;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AudioDescription implements MultimediaPart {

    Logger log = LoggerFactory.getLogger(getClass());
    private Segment segment;
    public final CompositionInstruction compositionInstruction;
    final Track track;
    private URL url;
    private String checksum;
    private URL urlInLocalCache = null;

    public AudioDescription(Segment segment, CompositionInstruction compositionInstruction, Track track, URL url, String checksum) {
        this.segment = segment;
        this.compositionInstruction = compositionInstruction;
        this.track = track;
        this.url = url;
        this.checksum = checksum;
    }

    public AudioInstruction asInstruction(Float masterBpm) {
        Float speedFactor = 44100 * 60 / track.bpm;
        Float differenceBetweenMasterSongAndPart = track.bpm / masterBpm;
        //Start and end come from the composition instructions
        int _start = new Float(compositionInstruction.start() * speedFactor * differenceBetweenMasterSongAndPart).intValue();
        int _end = new Float(compositionInstruction.end() * speedFactor * differenceBetweenMasterSongAndPart).intValue();
        //The cue is where to start inside the mp3 sample
        Float _cue = segment.start * speedFactor + track.mediaFile.startingOffset * 44100;
        int _duration = _end - _start;

        if(urlInLocalCache == null)
            throw new RuntimeException(url + " had not been cached before creating instruction!");

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

    public CompositionInstruction compositionInstruction() {
        return compositionInstruction;
    }

    public void cache(DownloadAndParseFacade downloader) throws IOException {
        urlInLocalCache = downloader.fetchFromCache(url, checksum);
    }
}
