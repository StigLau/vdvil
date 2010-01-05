package no.bouvet.kpro.tagger;

import no.bouvet.kpro.renderer.*;
import no.bouvet.kpro.renderer.audio.*;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.AbstractPart;

/**
 * This is the master class, responsible for playing a small demoset of VDVIL music
 */
public class PlayStuff {
    private final Renderer renderer;
    private final Float masterBpm;

    public PlayStuff(Composition composition) throws Exception {
        masterBpm = composition.masterBpm;
        Instructions instructions = createInstructionsFromParts(composition);
        renderer = new Renderer(instructions);
        renderer.addRenderer(new AudioRenderer(new AudioPlaybackTarget()));
    }

    public static Instructions createInstructionsFromParts(Composition composition) throws Exception {
        Instructions instructions = new Instructions();
        for (AbstractPart part : composition.parts) {
            instructions.append(part.translateToInstruction(composition.masterBpm));
        }
        return instructions;
    }

    public void play(Float startCue) throws Exception {
        Float startCueInMillis = (startCue * 44100 * 60)/ masterBpm;
        renderer.start(startCueInMillis.intValue());
    }

    public void stop() {
        renderer.stop();
    }
}
