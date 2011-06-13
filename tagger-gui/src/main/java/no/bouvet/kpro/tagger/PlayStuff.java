package no.bouvet.kpro.tagger;

import no.bouvet.kpro.renderer.*;
import no.bouvet.kpro.renderer.audio.*;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.AbstractPart;
import no.lau.vdvil.player.VdvilPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sound.sampled.LineUnavailableException;

/**
 * This is the master class, responsible for playing a small demoset of VDVIL music
 */
@Deprecated
public class PlayStuff implements VdvilPlayer{
    private final Renderer renderer;
    private final Float masterBpm;
    Logger log = LoggerFactory.getLogger(getClass());

    public PlayStuff(Composition composition) {
        masterBpm = composition.masterBpm;
        Instructions instructions = createInstructionsFromParts(composition);
        renderer = new Renderer(instructions);
        renderer.addRenderer(new AudioRenderer(new AudioPlaybackTarget()));
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

    public void play(int startAt) {
        Float startCueInMillis = (startAt * 44100 * 60)/ masterBpm;
        renderer.start(startCueInMillis.intValue());
    }

    public void stop() {
        renderer.stop();
    }

    public boolean isPlaying() {
        return renderer.isRendering();
    }
}
