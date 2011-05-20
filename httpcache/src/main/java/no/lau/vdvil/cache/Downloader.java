package no.lau.vdvil.cache;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * The simplest possible interface for a downloader
 */
public interface Downloader {
    InputStream fetchAsStream(URL url) throws IOException;
}
