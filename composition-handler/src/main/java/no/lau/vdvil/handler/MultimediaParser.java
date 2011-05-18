package no.lau.vdvil.handler;

import java.io.InputStream;

public interface MultimediaParser{
    MultimediaPart parse(InputStream inputStream) throws Exception;
}
