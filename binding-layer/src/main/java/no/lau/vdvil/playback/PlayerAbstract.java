package no.lau.vdvil.playback;

import no.bouvet.kpro.renderer.Instructions;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.ParseFacade;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.handler.persistence.MutableCompositionInstruction;
import no.lau.vdvil.player.InstructionPlayer;
import no.lau.vdvil.player.VdvilPlayer;
import no.lau.vdvil.renderer.Renderer;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.AudioDescription;
import no.vdvil.renderer.image.cacheinfrastructure.ImageDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stig@Lau.no - 25/12/14.
 */
public class PlayerAbstract implements VdvilPlayer{
    static Logger log = LoggerFactory.getLogger(PreconfiguredWavSerializer.class);
    public ParseFacade PARSE_FACADE;

    List<Renderer> renderers;

    VdvilPlayer player = VdvilPlayer.NULL;
    static Store store = Store.get();
    private boolean initialized = false;

    public VdvilPlayer init(Composition composition) {
        return init(composition, composition.masterBeatPattern);
    }

    public VdvilPlayer init(Composition composition, MasterBeatPattern beatPatternFilter) {
        if (isPlaying()) {
            throw new RuntimeException("Don't change the player during playback. Please stop first");
        }
        player = new InstructionPlayer(beatPatternFilter, new Instructions(), renderers);
        try {
            addComposition(composition, beatPatternFilter, 0);
        } catch (IOException e) {
            log.error("These errors should not happen", e);
        }
        this.initialized = true;
        return this;
    }

    public void addComposition(Composition composition, MasterBeatPattern beatPatternFilter, Integer cueDifference) throws IOException {
        Composition cachedComposition = cache(filterByTime(composition, beatPatternFilter));
        Instructions instructions = cachedComposition.instructions(beatPatternFilter.masterBpm, cueDifference);
        ((InstructionPlayer) player).appendInstructions(instructions);
    }

    public static Composition filterByTime(Composition composition, MasterBeatPattern filter) {
        List<MultimediaPart> filteredPartsList = new ArrayList<>();
        for (MultimediaPart multimediaPart : composition.multimediaParts) {
            CompositionInstruction instruction = multimediaPart.compositionInstruction();

            if(filter.fromBeat <= instruction.start() && instruction.end() <= filter.toBeat) {
                filteredPartsList.add(multimediaPart);
            } else if(instruction.end() <= filter.fromBeat || filter.toBeat <= instruction.start()) {
                //Is outside filters range
                log.debug("AbstractInstruction {} starting at {} was filtered out of the composition", multimediaPart, instruction.start());
            }else {
                if(instruction.start() < filter.fromBeat) {
                    //Crop Start
                    log.debug("AbstractInstruction {} fromBeat was set to {} to hit correct start time", multimediaPart, filter.fromBeat);
                    int startCrop = filter.fromBeat - instruction.start();
                    ((MutableCompositionInstruction) multimediaPart.compositionInstruction()).setCueDifference(startCrop);
                    Integer oldDuration = multimediaPart.compositionInstruction().duration();
                    ((MutableCompositionInstruction) multimediaPart.compositionInstruction()).setDuration(oldDuration - startCrop);
                }
                if(filter.toBeat < instruction.end()) {
                    log.debug("AbstractInstruction {} endBeat was set to {} because it ended to late", multimediaPart, filter.toBeat);
                    //Crop duration
                    int start = multimediaPart.compositionInstruction().start();
                    ((MutableCompositionInstruction) multimediaPart.compositionInstruction()).setDuration(filter.toBeat - start);
                }
                filteredPartsList.add(multimediaPart);
            }
        }
        return new Composition(composition.name, filter, filteredPartsList, composition.fileRepresentation());
    }

    /**
     * Cache the different parts of a Composition
     */
    public static Composition cache(Composition composition) throws IOException {
        composition.updateFileRepresentation(store.cache(composition.fileRepresentation));
        for (MultimediaPart part : composition.multimediaParts) {
            if (part instanceof AudioDescription) {
                ((AudioDescription)part).updateFileRepresentation(store.cache(part.fileRepresentation()));
            }if (part instanceof ImageDescription) {
                ((ImageDescription)part).updateFileRepresentation(store.cache(part.fileRepresentation()));
            }
        }
        return composition;
    }

    public VdvilPlayer play() {
        if(!initialized || player == NULL)
            throw new RuntimeException(getClass().getSimpleName() + ".init has not been run!");
        player.play();
        return this;
    }

    @Deprecated //Should perhaps not use this....
    public void playUntilEnd() {
        try {
            play();
            while (isPlaying())
                Thread.sleep(200);
            stop();
        } catch (Exception e) {
            throw new RuntimeException("This should not happen", e);
        }
    }

    public VdvilPlayer stop() {
        if(isPlaying())
            player.stop();
        return this;
    }

    public boolean isPlaying() {
        return player != NULL && player.isPlaying();
    }
}
