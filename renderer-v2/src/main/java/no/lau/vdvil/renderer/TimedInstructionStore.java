package no.lau.vdvil.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimedInstructionStore {
    Map<Long, List<Instruction>> timedInstructions = new HashMap<Long, List<Instruction>>();

    public void put(Instruction instruction) {
        Long storeKey = instruction.start();
        if (timedInstructions.get(storeKey) != null) {
            timedInstructions.get(storeKey).add(instruction);
        } else {
            ArrayList<Instruction> singletonList = new ArrayList<Instruction>();
            singletonList.add(instruction);
            timedInstructions.put(storeKey, singletonList);
        }
    }

    public boolean contains(long beat) {
        return timedInstructions.containsKey(beat);
    }

    public List<Instruction> get(long beat) {
        return timedInstructions.get(beat);
    }
}
