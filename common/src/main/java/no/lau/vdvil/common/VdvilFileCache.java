package no.lau.vdvil.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Simple cache for fetching files on disk based on URL
 */
public interface VdvilFileCache {
    public File fetchFromRepository(String url) throws FileNotFoundException;

    public InputStream fetchAsStream(String urlOfFile) throws FileNotFoundException;

    public InputStream fetchAsStream(String urlOfFile, String checksum) throws FileNotFoundException;
}
