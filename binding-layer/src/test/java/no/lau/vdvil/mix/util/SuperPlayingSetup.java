package no.lau.vdvil.mix.util;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.ParseFacade;
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
import java.net.URL;

public abstract class SuperPlayingSetup {
    protected PreconfiguredVdvilPlayer vdvilPlayer;
    public static ParseFacade parser = PreconfiguredVdvilPlayer.PARSE_FACADE;

    public abstract Composition compose(MasterBeatPattern masterBeatPattern) throws IOException;

    public SuperPlayingSetup() {
        vdvilPlayer = new PreconfiguredVdvilPlayer();
    }

    @Deprecated
    //TODO Refactor this into PreCondiguredVdvilPlayer
    public void play(MasterBeatPattern masterBeatPattern) {
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

    public static MultimediaPart createAudioPart(String id, TimeInterval timeInterval, URL url) {
        try { return parser.parse(new PartXML(id, timeInterval, new DvlXML("URL Name", url)));
        } catch (IOException e) { throw new RuntimeException(e); }
    }
    public static MultimediaPart createLyricPart(String text, TimeInterval timeInterval) {
        return new LyricDescription(text, new PartXML(text, timeInterval, new DvlXML("name", null)));
    }

    public static MultimediaPart createImagePart(String id, TimeInterval timeInterval, FileRepresentation fileRepresentation) {
        return new ImageDescription(new PartXML(id, timeInterval, new DvlXML(id, null)), fileRepresentation);
    }

    protected static MultimediaPart createPart(TimeInterval timeInterval, Segment segment, Track track, URL url) {
        return new AudioDescription(segment, new PartXML(segment.id, timeInterval, new DvlXML("", url)), track);
    }
}