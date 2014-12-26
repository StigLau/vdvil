package no.lau.vdvil.playback;

import no.bouvet.kpro.renderer.audio.AudioRenderer;
import no.bouvet.kpro.renderer.audio.WaveFileTarget;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.parser.json.CompositionJsonParser;
import no.lau.vdvil.player.VdvilPlayer;
import no.vdvil.parser.audio.json.AudioJsonParser;
import no.vdvil.renderer.audio.AudioXMLParser;
import java.io.File;

public class PreconfiguredWavSerializer extends PlayerAbstract implements VdvilPlayer {

    public PreconfiguredWavSerializer(File resultingFile) {
        renderers.add(new AudioRenderer(new WaveFileTarget(resultingFile)));

        PARSE_FACADE = new ParseFacade();
        PARSE_FACADE.addParser(new CompositionXMLParser(PARSE_FACADE));
        PARSE_FACADE.addParser(new CompositionJsonParser(PARSE_FACADE));
        PARSE_FACADE.addParser(new AudioXMLParser());
        PARSE_FACADE.addParser(new AudioJsonParser());
    }
}
