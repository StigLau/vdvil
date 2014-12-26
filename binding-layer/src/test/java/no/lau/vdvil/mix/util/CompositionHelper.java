package no.lau.vdvil.mix.util;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.Compositeur;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.persistence.DvlXML;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.TimeInterval;
import no.vdvil.renderer.audio.AudioDescription;
import no.vdvil.renderer.audio.Segment;
import no.vdvil.renderer.audio.Track;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescription;
import no.vdvil.renderer.lyric.LyricDescription;
import java.io.IOException;
import java.net.URL;

/**
 * @author Stig@Lau.no - 25/12/14.
 * Functionality for creating Compositions for test usage
 */
public abstract class CompositionHelper implements Compositeur {

    public static ParseFacade parser = new PreconfiguredVdvilPlayer().PARSE_FACADE;

    public static MultimediaPart createAudioPart(String id, TimeInterval timeInterval, FileRepresentation fileRepresentation) {
        try { return parser.parse(new PartXML(id, timeInterval, new DvlXML(fileRepresentation)));
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
