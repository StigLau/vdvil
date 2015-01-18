package no.lau.vdvil.cache;

import java.net.URL;

/**
 * Interface for a cache
 */
public interface SimpleVdvilCache{
    boolean accepts(URL url);

    String mimeType(URL url);
}
