package no.lau.vdvil.composition;

import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.instruction.Instruction;

/**
 * An adapter to convert the Vdvil v1 format to v2 format
 */
public class CompositionAdapter {
    public static Instruction[] convert(MultimediaPart[] multimediaParts) {
        Instruction[] instructions = new Instruction[multimediaParts.length];
        for (int i = 0; i < multimediaParts.length; i++) {
            instructions[i] = multimediaParts[i].asV2Instruction();
        }
        return instructions;
    }
}
