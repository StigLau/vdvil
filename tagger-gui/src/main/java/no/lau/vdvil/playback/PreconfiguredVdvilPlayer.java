package no.lau.vdvil.playback;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.audio.AudioPlaybackTarget;
import no.bouvet.kpro.renderer.audio.AudioRenderer;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.handler.persistence.SimpleFileCache;
import no.lau.vdvil.player.InstructionPlayer;
import no.lau.vdvil.player.VdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.AudioXMLParser;
import no.vdvil.renderer.image.ImageRenderer;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PreconfiguredVdvilPlayer implements VdvilPlayer {

    Logger log = LoggerFactory.getLogger(getClass());
    DownloadAndParseFacade downloadAndParseFacade;
    List<? extends AbstractRenderer> renderers;

    VdvilPlayer player = VdvilPlayer.NULL;

    public PreconfiguredVdvilPlayer() {
        downloadAndParseFacade = new DownloadAndParseFacade();
        downloadAndParseFacade.addCache(VdvilHttpCache.create());
        downloadAndParseFacade.addCache(new SimpleFileCache()); //For local file access
        downloadAndParseFacade.addParser(new CompositionXMLParser(downloadAndParseFacade));
        downloadAndParseFacade.addParser(new AudioXMLParser(downloadAndParseFacade));
        downloadAndParseFacade.addParser(new ImageDescriptionXMLParser(downloadAndParseFacade));

        renderers = Arrays.asList(
                new ImageRenderer(800, 600, downloadAndParseFacade),
                new AudioRenderer(new AudioPlaybackTarget()));
    }

    public void init(Composition composition, MasterBeatPattern beatPattern) throws IllegalAccessException {
        if(isPlaying())
            throw new IllegalAccessException("Don't change the player during playback. Please stop first");
        try {
            Instructions instructions = composition.instructions(beatPattern.masterBpm);
            player = new InstructionPlayer(beatPattern.masterBpm, instructions, renderers);

        } catch (IOException e) {
            log.error("These errors should not happen", e);
        }

    }

    public DownloadAndParseFacade accessCache() {
        return downloadAndParseFacade;
    }

    public void play(int startAt) {
        player.play(startAt);
    }

    public void stop() {
        player.stop();
    }

    public boolean isPlaying() {
        return player.isPlaying();
    }
}
