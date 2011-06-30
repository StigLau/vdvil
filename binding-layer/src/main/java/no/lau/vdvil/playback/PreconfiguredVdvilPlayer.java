package no.lau.vdvil.playback;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.audio.AudioPlaybackTarget;
import no.bouvet.kpro.renderer.audio.AudioRenderer;
import no.lau.vdvil.cache.SimpleCacheImpl;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.*;
import no.lau.vdvil.player.InstructionPlayer;
import no.lau.vdvil.player.VdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.AudioXMLParser;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import no.vdvil.renderer.image.ImageRenderer;
import no.vdvil.renderer.lyric.LyricRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreconfiguredVdvilPlayer implements VdvilPlayer {

    static Logger log = LoggerFactory.getLogger(PreconfiguredVdvilPlayer.class);
    DownloadAndParseFacade downloadAndParseFacade;
    List<? extends AbstractRenderer> renderers;

    VdvilPlayer player = VdvilPlayer.NULL;

    public PreconfiguredVdvilPlayer() {
        downloadAndParseFacade = new DownloadAndParseFacade();
        downloadAndParseFacade.addCache(new SimpleCacheImpl());
        //downloadAndParseFacade.addCache(VdvilHttpCache.create());
        downloadAndParseFacade.addCache(new SimpleCacheImpl()); //For local file access
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
                    timeFilteredComposition.masterBeatPattern,
                    timeFilteredComposition.instructions(timeFilteredComposition.masterBeatPattern.masterBpm),
                    renderers);
        } catch (IOException e) {
            log.error("These errors should not happen", e);
        }
    }

    public static Composition filterByTime(Composition composition, MasterBeatPattern filter) {
        List<MultimediaPart> filteredPartsList = new ArrayList<MultimediaPart>();
        for (MultimediaPart multimediaPart : composition.multimediaParts) {
            CompositionInstruction instruction = multimediaPart.compositionInstruction();

            if(filter.fromBeat <= instruction.start() && instruction.end() <= filter.toBeat) {
                filteredPartsList.add(multimediaPart);
            } else if(instruction.end() <= filter.fromBeat || filter.toBeat <= instruction.start()) {
                //Is outside
                log.info("Instruction {} starting at {} was filtered out of the composition", multimediaPart, instruction.start());
            }else {
                if(instruction.start() < filter.fromBeat) {
                    //Crop Start
                    log.info("Instruction {} fromBeat was set to {} to hit correct start time", multimediaPart, filter.fromBeat);
                    ((MutableCompositionInstruction) multimediaPart.compositionInstruction()).setStart(filter.fromBeat);
                }
                if(filter.toBeat < instruction.end()) {
                    log.info("Instruction {} endBeat was set to {} because it ended to late", multimediaPart, filter.toBeat);
                    ((MutableCompositionInstruction) multimediaPart.compositionInstruction()).setEnd(filter.toBeat);
                    //Crop End
                }
                filteredPartsList.add(multimediaPart);
            }
        }
        return new Composition(composition.name, filter, filteredPartsList, composition.url);
    }

    public DownloadAndParseFacade accessCache() {
        return downloadAndParseFacade;
    }

    public void play() {
        if(player == NULL)
            throw new RuntimeException(getClass().getSimpleName() + ".init has not been run!");
        player.play();
    }

    public void stop() {
        if(isPlaying())
            player.stop();
    }

    public boolean isPlaying() {
        return player != NULL && player.isPlaying();
    }
}
