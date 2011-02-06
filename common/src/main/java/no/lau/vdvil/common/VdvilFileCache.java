package no.lau.vdvil.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Simple cache for fetching files on disk based on URL
 */
public interface VdvilFileCache {

    /**
     * Performs a dual-check to verify if the file exists in both repository and has correct checksum
     * @param url location on the internet
     * @param checksum file MD5 checksum to verify with
     * @return true if both both are correct
     */
    boolean existsInRepository(String url, String checksum);

    File fetchFromRepository(String url) throws FileNotFoundException;

    InputStream fetchAsStream(String urlOfFile) throws FileNotFoundException;

    InputStream fetchAsStream(String urlOfFile, String checksum) throws FileNotFoundException;
}
