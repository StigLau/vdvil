package no.lau.vdvil.handler;

import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A frontend for multiple Caches and Parsers
 */
public class ParseFacade implements MultimediaParser {
    Logger log = LoggerFactory.getLogger(getClass());
    List<MultimediaParser> parsers = new ArrayList<MultimediaParser>();
    Store store = Store.get();

    public static final ParseFacade NULL = new ParseFacade();

    /**
     * Tries to download a DVL and see if a Parser will create a MultimediaPart from it.
     * @param instruction with the relevant data
     * @return a parsed MultimediaPart. MultimediaPart.NULL if unsuccessful
     */
    public MultimediaPart parse(CompositionInstruction instruction) throws IOException{
        store.cache(instruction.dvl().url());
        for (MultimediaParser parser : parsers) {
            try {
                return parser.parse(instruction);
            } catch (Exception e) {
                log.debug("{} could not parse {}", parser.getClass().getSimpleName(), instruction.dvl().name());
                log.debug("Parsing error: ", e);
            }
        }
        String parserList = "";
        for (MultimediaParser parser : parsers) {
            parserList += parser.getClass().getSimpleName() + " ";
        }
        throw new IOException("No parsers able to parse " + instruction.dvl().name() + ", check debug log! Tried parsers: " + parserList);
    }

    public String toString(){
        return getClass().getName();
    }

    public void addParser(MultimediaParser parser) {
        this.parsers.add(parser);
    }
}
