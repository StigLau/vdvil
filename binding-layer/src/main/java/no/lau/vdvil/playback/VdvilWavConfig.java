package no.lau.vdvil.playback;

import no.bouvet.kpro.renderer.audio.AudioRenderer;
import no.bouvet.kpro.renderer.audio.WaveFileTarget;
import no.lau.MD5;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.parser.json.CompositionJsonParser;
import no.lau.vdvil.player.VdvilPlayerConfig;
import no.lau.vdvil.renderer.Renderer;
import no.vdvil.parser.audio.json.AudioJsonParser;
import no.vdvil.renderer.audio.AudioXMLParser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VdvilWavConfig implements VdvilPlayerConfig {
    public final File resultingFile;

    /**
     * As a testfile, the file will be deleted on exit
     */
    public VdvilWavConfig(Object fileOwner) {
        String filename = fileOwner.getClass().getSimpleName();
        try {
            resultingFile = File.createTempFile(filename, ".wav");
            resultingFile.deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException("Could not create tempfile " + filename, e);
        }
    }

    public String checksum() throws IOException {
        return MD5.md5Hex(Files.readAllBytes(Paths.get(resultingFile.toURI())));
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
