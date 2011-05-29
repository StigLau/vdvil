package no.lau.vdvil.handler;

import java.io.IOException;
import java.net.URL;

public interface MultimediaParser{
    /**
     *
     * @param id of an element inside the multimediaObject. Used in cases where more than one exists. Empty if not relevant for mediatype
     * @param url to relevant media
     * @return a parsed MultimediaPart
     * @throws IOException if shite happens
     */
    MultimediaPart parse(String id, URL url) throws IOException;
}
