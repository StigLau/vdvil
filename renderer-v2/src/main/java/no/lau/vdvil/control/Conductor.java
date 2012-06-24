package no.lau.vdvil.control;

import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.renderer.TimedInstructionStore;
import no.lau.vdvil.timing.BeatTimeConverter;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.lau.vdvil.timing.ResolutionTimer;
import no.lau.vdvil.timing.RunnableResolutionTimer;
import no.no.lau.vdvil.renderer.Renderer;

/**
 * Knower of all instructions and renderers
 * @author Stig Lau
 * @since June 2012
 */
public class Conductor {
    TimedInstructionStore timedInstructionStore = new TimedInstructionStore();
    BeatTimeConverter converter;

    public Conductor(ResolutionTimer timer, int speed) {
        converter = new BeatTimeConverter(this, timer, speed);

    }

    public Conductor(RunnableResolutionTimer timer, MasterBeatPattern masterBeatPattern) {
        this(timer, masterBeatPattern.masterBpm.intValue());
    }

    public void addInstruction(Renderer renderer, Instruction... instructions) {
        for (Instruction instruction : instructions) {
            timedInstructionStore.put(renderer, instruction);
        }
    }

    public void notify(long beat) {
        for (Instruction instruction : timedInstructionStore.get(beat)) {
            Renderer renderer = timedInstructionStore.owningRenderer(instruction);
            renderer.notify(instruction, beat);
        }
    }

    public boolean isPlaying() {
        return timedInstructionStore.passedInstructions(converter.getCurrentBeat());
    }
}
