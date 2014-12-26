package no.lau.vdvil.playback;

import no.bouvet.kpro.renderer.audio.AudioPlaybackTarget;
import no.bouvet.kpro.renderer.audio.AudioRenderer;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.persistence.*;
import no.lau.vdvil.parser.json.CompositionJsonParser;
import no.vdvil.parser.audio.json.AudioJsonParser;
import no.vdvil.renderer.audio.AudioXMLParser;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import no.vdvil.renderer.image.ImageRenderer;
import no.vdvil.renderer.image.cacheinfrastructure.OnlyTheImageDescriptionParser;
import no.vdvil.renderer.lyric.LyricRenderer;
import java.util.Arrays;

public class PreconfiguredVdvilPlayer extends PlayerAbstract  {

    public PreconfiguredVdvilPlayer() {
        try {
            renderers = Arrays.asList(
                    new ImageRenderer(800, 600),
                    new LyricRenderer(800, 100),
                    new AudioRenderer(new AudioPlaybackTarget())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        PARSE_FACADE = new ParseFacade();
        PARSE_FACADE.addParser(new CompositionXMLParser(PARSE_FACADE));
        PARSE_FACADE.addParser(new CompositionJsonParser(PARSE_FACADE));
        PARSE_FACADE.addParser(new AudioXMLParser());
        PARSE_FACADE.addParser(new AudioJsonParser());
        PARSE_FACADE.addParser(new ImageDescriptionXMLParser());
        PARSE_FACADE.addParser(new OnlyTheImageDescriptionParser());
    }
}
