package no.lau.vdvil.renderer;

import java.util.*;

public class SimpleTestTimer implements Timer{
    Map<Integer, List<Instruction>> timedInstructions = new HashMap<Integer, List<Instruction>>();
    private long waitFor;

    public SimpleTestTimer(int speed, int divider) {
        waitFor = 1000 * divider / speed;
    }

    public void play(int fromBeat, int toBeat) throws InterruptedException {
        for (int beat = fromBeat; beat < toBeat; beat++) {
            List<Instruction> instructions = timedInstructions.get(beat);
            if(instructions != null) {
                for (Instruction instruction : instructions) {
                    System.out.println("Beat " + beat + "  instruction = " + instruction);
                }
            } else {
                System.out.println(beat);
            }

            Thread.sleep(waitFor);
        }
    }

    public void addInstruction(int beat, Instruction instruction) {
        if(timedInstructions.get(beat) == null) {
            ArrayList<Instruction> singletonList = new ArrayList<Instruction>();
            singletonList.add(instruction);
            timedInstructions.put(beat, singletonList);
        } else {
            timedInstructions.get(beat).add(instruction);
        }
    }
}
