package no.lau.vdvil.cache;

import java.net.URL;

/**
 * Interface for a cache
 */
public interface SimpleVdvilCache extends Downloader {
    boolean accepts(URL url);

    String mimeType(URL url);
}
