package no.lau.vdvil.composition;

import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.instruction.Instruction;
import java.io.IOException;

/**
 * An adapter to cacheAndconvert the Vdvil v1 format to v2 format
 */
public class CompositionAdapter {
    public static Instruction[] cacheAndconvert(Store store, MultimediaPart[] multimediaParts) throws IOException {
        Instruction[] instructions = new Instruction[multimediaParts.length];
        for (int i = 0; i < multimediaParts.length; i++) {
            MultimediaPart part = multimediaParts[i];
            store.cache(part.fileRepresentation());
            instructions[i] = part.asV2Instruction();
        }
        return instructions;
    }
}
