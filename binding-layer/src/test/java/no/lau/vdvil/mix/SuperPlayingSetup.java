package no.lau.vdvil.mix;

import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.DvlXML;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.lau.vdvil.timing.TimeInterval;
import no.vdvil.renderer.audio.AudioDescription;
import no.vdvil.renderer.audio.Segment;
import no.vdvil.renderer.audio.Track;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescription;
import no.vdvil.renderer.lyric.LyricDescription;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class SuperPlayingSetup {
    protected PreconfiguredVdvilPlayer vdvilPlayer = new PreconfiguredVdvilPlayer();
    protected DownloadAndParseFacade downloader = PreconfiguredVdvilPlayer.downloadAndParseFacade;

    protected abstract Composition compose(MasterBeatPattern masterBeatPattern) throws IOException;

    protected void play(MasterBeatPattern masterBeatPattern) {
        try {
            vdvilPlayer.init(compose(masterBeatPattern));
            vdvilPlayer.play();
            while (vdvilPlayer.isPlaying())
                Thread.sleep(200);
            vdvilPlayer.stop();
        } catch (Exception e) {
            throw new RuntimeException("This should not happen", e);
        }
    }

    protected static MultimediaPart createAudioPart(String id, TimeInterval timeInterval, URL url, DownloadAndParseFacade cache) throws IOException {
        return cache.parse(new PartXML(id, timeInterval, new DvlXML("URL Name", url)));
    }
    protected static MultimediaPart createLyricPart(String text, TimeInterval timeInterval) throws MalformedURLException {
        return new LyricDescription(text, new PartXML(text, timeInterval, new DvlXML("name", new URL("http://url.com"))));
    }

    protected static MultimediaPart createImagePart(String id, TimeInterval timeInterval, URL url) throws MalformedURLException {
        return new ImageDescription(new PartXML(id, timeInterval, new DvlXML(id, null)), url);
    }

    protected static MultimediaPart createPart(TimeInterval timeInterval, Segment segment, Track track) {
        return new AudioDescription(segment, new PartXML(segment.id, timeInterval, new DvlXML("", track.mediaFile.fileName)), track);
    }
}