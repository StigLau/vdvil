package no.lau.vdvil.playback;

import no.bouvet.kpro.renderer.Instructions;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.handler.persistence.MutableCompositionInstruction;
import no.lau.vdvil.player.InstructionPlayer;
import no.lau.vdvil.player.VdvilPlayer;
import no.lau.vdvil.player.VdvilPlayerConfig;
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
 * Backstage is where the preperation before performing is done;
 * Deserilizing compositions, caching files and preparing object graphs.
 * @author Stig@Lau.no - 25/12/14.
 */
public class BackStage {
    static Logger log = LoggerFactory.getLogger(BackStage.class);

    List<Renderer> renderers;

    static Store store = Store.get();

    /**
     * Playback to audio Config
     */
    public BackStage() {
        renderers = new VdvilAudioConfig().getRenderers();
    }

    /**
     * Choose playback configuration, like wav persistence
     */
    public BackStage(VdvilPlayerConfig config) {
        renderers = config.getRenderers();
    }


    public VdvilPlayer prepare(Composition composition) {
        return prepare(composition, composition.masterBeatPattern);
    }

    public VdvilPlayer prepare(Composition composition, MasterBeatPattern beatPatternFilter) {
        InstructionPlayer player = new InstructionPlayer(beatPatternFilter, new Instructions(), renderers);
        try {
            addComposition(player, composition, beatPatternFilter, 0);
        } catch (IOException e) {
            log.error("These errors should not happen", e);
        }
        return player;
    }

    public void addComposition(InstructionPlayer player, Composition composition, MasterBeatPattern beatPatternFilter, Integer cueDifference) throws IOException {
        Composition cachedComposition = cache(filterByTime(composition, beatPatternFilter));
        Instructions instructions = cachedComposition.instructions(beatPatternFilter.masterBpm, cueDifference);
        player.appendInstructions(instructions);
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
        return new Composition(composition.name, filter, composition.fileRepresentation(), () -> filteredPartsList);
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
}
