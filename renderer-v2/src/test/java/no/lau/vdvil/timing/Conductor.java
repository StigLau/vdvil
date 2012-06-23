package no.lau.vdvil.timing;

import no.lau.vdvil.renderer.Instruction;
import no.lau.vdvil.renderer.Renderer;
import no.lau.vdvil.renderer.TimedInstructionStore;

/**
 * Knower of all instructions and renderers
 * @author Stig Lau
 * @since June 2012
 */
public class Conductor {
    TimedInstructionStore timedInstructionStore = new TimedInstructionStore();
    BeatTimeConverter converter;

    public Conductor(ResolutionTimer timer, int speed, int beatsInAMinute) {
        converter = new BeatTimeConverter(this, timer, speed, beatsInAMinute);

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
}
