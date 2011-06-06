package no.lau.vdvil.handler;

import no.lau.vdvil.handler.persistence.CompositionInstruction;
import java.io.IOException;

public interface MultimediaParser{
    /**
     *
     * @param compositionInstruction contains all the instructions from the composition
     * @return a parsed MultimediaPart
     * @throws IOException if shite happens
     */
    MultimediaPart parse(CompositionInstruction compositionInstruction) throws IOException;
}
