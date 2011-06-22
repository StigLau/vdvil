package no.lau.vdvil.playback;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.audio.AudioPlaybackTarget;
import no.bouvet.kpro.renderer.audio.AudioRenderer;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.handler.persistence.CompositionXMLParser;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.handler.persistence.SimpleFileCache;
import no.lau.vdvil.player.InstructionPlayer;
import no.lau.vdvil.player.VdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.AudioXMLParser;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import no.vdvil.renderer.image.ImageRenderer;
import no.vdvil.renderer.lyric.LyricRenderer;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
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
                new LyricRenderer(800, 100),
                new AudioRenderer(new AudioPlaybackTarget()));
    }

    public void init(Composition composition) throws IllegalAccessException {
        init(composition, composition.masterBeatPattern);
    }

    public void init(Composition composition, MasterBeatPattern beatPatternFilter) throws IllegalAccessException {
        if(isPlaying())
            throw new IllegalAccessException("Don't change the player during playback. Please stop first");
        try {
            Composition timeFilteredComposition = filterByTime(composition, beatPatternFilter);
            composition.cache(accessCache());
            player = new InstructionPlayer(
                    timeFilteredComposition.masterBeatPattern.masterBpm,
                    timeFilteredComposition.instructions(timeFilteredComposition.masterBeatPattern.masterBpm),
                    renderers);
        } catch (IOException e) {
            log.error("These errors should not happen", e);
        }
    }

    private Composition filterByTime(Composition composition, MasterBeatPattern filterBeatPattern) {
        List<MultimediaPart> filteredPartsList = new ArrayList<MultimediaPart>();
        for (MultimediaPart multimediaPart : composition.multimediaParts) {
            CompositionInstruction instruction = multimediaPart.compositionInstruction();
            if(instruction.start() < filterBeatPattern.toBeat) {
                if(instruction.end() > filterBeatPattern.toBeat) {
                    log.info("Instruction {} endBeat was set to {} because it ended to late", multimediaPart, filterBeatPattern.toBeat);
                    ((PartXML)multimediaPart.compositionInstruction()).setEnd(filterBeatPattern.toBeat);
                }
                filteredPartsList.add(multimediaPart);
            }
            else
                log.info("Instruction {} starting at {} was filtered out of the composition", multimediaPart, instruction.start());
        }
        return new Composition(composition.name, filterBeatPattern, filteredPartsList, composition.url);
    }

    public DownloadAndParseFacade accessCache() {
        return downloadAndParseFacade;
    }

    public void play(int startAt) {
        ifPlayerIsNullThrowException();
        player.play(startAt);
    }

    public void stop() {
        ifPlayerIsNullThrowException();
        player.stop();
    }

    public boolean isPlaying() {
        if(player == NULL)
            return false;
        return player.isPlaying();
    }

    private void ifPlayerIsNullThrowException() {
        if(player == NULL)
            throw new RuntimeException(getClass().getSimpleName() + ".init has not been run!");
    }
}
