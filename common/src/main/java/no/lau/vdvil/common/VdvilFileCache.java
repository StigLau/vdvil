package no.lau.vdvil.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Simple cache for fetching files on disk based on URL
 */
public interface VdvilFileCache {

    InputStream fetchAsStream(String urlOfFile) throws FileNotFoundException;

    /**
     * The preferred way of downloading files from the web. Does not matter whether the file could be persisted to cache or not
     * @param urlOfFile
     * @param checksum
     * @return
     * @throws FileNotFoundException
     */
    InputStream fetchAsStream(String urlOfFile, String checksum) throws FileNotFoundException;

    File fetchAsFile(String url, String checksum) throws FileNotFoundException;
}
