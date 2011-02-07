package no.bouvet.kpro.tagger;

import no.bouvet.kpro.renderer.*;
import no.bouvet.kpro.renderer.audio.*;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.AbstractPart;
import org.apache.log4j.Logger;
import javax.sound.sampled.LineUnavailableException;

/**
 * This is the master class, responsible for playing a small demoset of VDVIL music
 */
public class PlayStuff {
    private final Renderer renderer;
    private final Float masterBpm;
    static Logger log = Logger.getLogger(PlayStuff.class);

    public PlayStuff(Composition composition) {
        masterBpm = composition.masterBpm;
        Instructions instructions = createInstructionsFromParts(composition);
        renderer = new Renderer(instructions);
        try {
            renderer.addRenderer(new AudioRenderer(new AudioPlaybackTarget()));
        } catch (LineUnavailableException e) {
            log.error("Problem opening Audio line", e);
        }
    }

    public Instructions createInstructionsFromParts(Composition composition) {
        Instructions instructions = new Instructions();
        for (AbstractPart part : composition.parts) {
            try {
                instructions.append(part.translateToInstruction(composition.masterBpm));
            } catch (Exception e) {
                log.error("Error reading file: ", e);
            }
        }
        return instructions;
    }

    public void play(Float startCue) {
        Float startCueInMillis = (startCue * 44100 * 60)/ masterBpm;
        renderer.start(startCueInMillis.intValue());
    }

    public void stop() {
        renderer.stop();
    }
}
