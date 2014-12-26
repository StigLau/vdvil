package no.lau.vdvil.player;

import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.OldRenderer;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.lau.vdvil.renderer.Renderer;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Simplified player for different instructions
 * Can be improved with a
 */
public class InstructionPlayer implements VdvilPlayer {

    final OldRenderer renderer;
    final MasterBeatPattern masterBeatPattern;

    public InstructionPlayer(MasterBeatPattern masterBeatPattern, Instructions instructions, List<Renderer> renderers) {
        this.masterBeatPattern = masterBeatPattern;
        renderer = new OldRenderer(instructions);
        for (Renderer abstractRenderer : renderers) {
            renderer.addRenderer(abstractRenderer);
        }
    }

    public VdvilPlayer init(Composition composition) {
        return this;
    }

    public VdvilPlayer play() {
        MasterBeatPattern untilStart = new MasterBeatPattern(0, masterBeatPattern.fromBeat, masterBeatPattern.masterBpm);
        //TODO Note that there are potential problems here!!!
        Float duration = untilStart.durationCalculation() * Instruction.RESOLUTION / 1000;
        renderer.start(duration.intValue());
        return this;
    }

    public void playUntilEnd() {
        LoggerFactory.getLogger(getClass()).info("InstructionPlayer playing til the phat lady sings");
    }

    public VdvilPlayer stop() {
        renderer.stop();
        return this;
    }

    public boolean isPlaying() {
        return renderer.isRendering();
    }

    public void appendInstructions(Instructions instructions) {
        renderer.appendInstructions(instructions);
    }
}
