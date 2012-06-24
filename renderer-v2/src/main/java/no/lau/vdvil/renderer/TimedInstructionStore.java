package no.lau.vdvil.renderer;

import no.lau.vdvil.instruction.Instruction;
import no.no.lau.vdvil.renderer.Renderer;
import java.util.*;

public class TimedInstructionStore {
    Map<Long, List<Instruction>> timedInstructions = new HashMap<Long, List<Instruction>>();
    Map<Instruction, Renderer> instructionRendererMap = new HashMap<Instruction, Renderer>();
    long lastBeat = 0;

    /**
     * Stores both Renderer and Instruction so they are convenient to retrieve
     */
    public void put(Renderer renderer, Instruction instruction) {
        instructionRendererMap.put(instruction, renderer);

        Long storeKey = instruction.start();
        if (timedInstructions.get(storeKey) != null) {
            timedInstructions.get(storeKey).add(instruction);
        } else {
            ArrayList<Instruction> singletonList = new ArrayList<Instruction>();
            singletonList.add(instruction);
            timedInstructions.put(storeKey, singletonList);
        }
        lastBeat = calculateLastBeat(timedInstructions);
    }

    static long calculateLastBeat(Map<Long, List<Instruction>> timedInstructions) {
        long lastFoundBeat = 0;
        for (List<Instruction> instructions : timedInstructions.values()) {
            for (Instruction instruction : instructions) {
                long checkBeat = instruction.start() + instruction.length();
                if(lastFoundBeat < checkBeat)
                    lastFoundBeat = checkBeat;
            }
        }
        return lastFoundBeat;
    }

    /**
     * @param beat the starting time where there might be stored instructions with the time as key.
     * @return the collection of instructions at a given beat. An empty list if it was not found
     */
    public List<Instruction> get(long beat) {
        if(timedInstructions.containsKey(beat))
            return timedInstructions.get(beat);
        else
            return Collections.EMPTY_LIST;
    }

    public Renderer owningRenderer(Instruction instruction) {
        return instructionRendererMap.get(instruction);
    }

    public boolean passedInstructions(long beat) {
        return beat <= lastBeat;
    }
}
