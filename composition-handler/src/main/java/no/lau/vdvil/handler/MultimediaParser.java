package no.lau.vdvil.handler;

import java.io.IOException;
import java.net.URL;

public interface MultimediaParser{
    MultimediaPart parse(URL url) throws IOException;
}
