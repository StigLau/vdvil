package no.lau.vdvil.playback;

import no.bouvet.kpro.renderer.audio.AudioRenderer;
import no.bouvet.kpro.renderer.audio.WaveFileTarget;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.parser.json.CompositionJsonParser;
import no.lau.vdvil.player.VdvilPlayerConfig;
import no.lau.vdvil.renderer.Renderer;
import no.vdvil.parser.audio.json.AudioJsonParser;
import no.vdvil.renderer.audio.AudioXMLParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VdvilWavConfig implements VdvilPlayerConfig {
    private final File resultingFile;

    public VdvilWavConfig(File resultingFile) {
        this.resultingFile = resultingFile;
    }

    public ParseFacade getParseFacade() {
        ParseFacade PARSE_FACADE = new ParseFacade();
        PARSE_FACADE.addParser(new CompositionXMLParser(PARSE_FACADE));
        PARSE_FACADE.addParser(new CompositionJsonParser(PARSE_FACADE));
        PARSE_FACADE.addParser(new AudioXMLParser());
        PARSE_FACADE.addParser(new AudioJsonParser());
        return PARSE_FACADE;
    }

    public List<Renderer> getRenderers() {
        List<Renderer> renderers = new ArrayList<>();
        renderers.add(new AudioRenderer(new WaveFileTarget(resultingFile)));
        return renderers;
    }
}
