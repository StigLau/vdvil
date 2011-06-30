package no.lau.vdvil.player;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Simplified player for different instructions
 * Can be improved with a
 */
public class InstructionPlayer implements VdvilPlayer {

    final Renderer renderer;
    final int framerate = 44100;
    final MasterBeatPattern masterBeatPattern;
    Logger log = LoggerFactory.getLogger(getClass());

    public InstructionPlayer(MasterBeatPattern masterBeatPattern, Instructions instructions, List<? extends AbstractRenderer> renderers) {
        this.masterBeatPattern = masterBeatPattern;
        renderer = new Renderer(instructions);
        for (AbstractRenderer abstractRenderer : renderers) {
            renderer.addRenderer(abstractRenderer);
        }
    }

    public void play() {
        MasterBeatPattern untilStart = new MasterBeatPattern(0, masterBeatPattern.fromBeat, masterBeatPattern.masterBpm);
        Float duration = untilStart.durationCalculation() * framerate / 1000;
        log.error("duration = {} Note that there are problems here!!!", duration);
        renderer.start(duration.intValue());
    }

    public void stop() {
        renderer.stop();
    }

    public boolean isPlaying() {
        return renderer.isRendering();
    }
}
