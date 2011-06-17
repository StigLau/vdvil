package no.lau.vdvil.playback;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instruction;
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
import no.vdvil.renderer.image.ImageInstruction;
import no.vdvil.renderer.image.ImageRenderer;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescriptionXMLParser;
import no.vdvil.renderer.lyric.LyricRenderer;
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
                new LyricRenderer(800, 100),
                new AudioRenderer(new AudioPlaybackTarget()));
    }

    public void init(Composition composition, MasterBeatPattern beatPattern) throws IllegalAccessException {
        if(isPlaying())
            throw new IllegalAccessException("Don't change the player during playback. Please stop first");
        try {
            Instructions timeFilteredInstructions = filterByTime(composition.instructions(beatPattern.masterBpm), beatPattern);
            cacheInstructions(timeFilteredInstructions);
            player = new InstructionPlayer(beatPattern.masterBpm, timeFilteredInstructions, renderers);

        } catch (IOException e) {
            log.error("These errors should not happen", e);
        }

    }

    private Instructions filterByTime(Instructions instructions, MasterBeatPattern filterBeatPattern) {
        Instructions filteredInstructions = new Instructions();
        for (Instruction instruction : instructions.lock()) {
            if(instruction.getStart() < filterBeatPattern.durationCalculation()) {
                filteredInstructions.append(instruction);
                log.debug("Added instruction " + instruction.getStart() + " while maxtime was " + filterBeatPattern.durationCalculation());
            }
        }
        //To tell the renderer to stop after the last instruction
        instructions.endAt(filterBeatPattern.durationCalculation().intValue());
        log.debug("filterBeatPattern.durationCalculation().intValue() = " + filterBeatPattern.durationCalculation().intValue());
        return filteredInstructions;
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

    private void cacheInstructions(Instructions instructions) throws IOException {
        for (Instruction instruction : instructions.lock()) {
            log.debug("instruction.getStart() + instruction.getEnd()   = " + instruction.getClass().getSimpleName() + " " + instruction.getStart() + " " + instruction.getEnd());
            if (instruction instanceof ImageInstruction) {
                ((ImageInstruction) instruction).cache(downloadAndParseFacade);
            }
        }
        instructions.unlock();
    }
}
