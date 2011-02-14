package no.lau.vdvil.cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * A generic interface for all caches
 */
public interface VdvilCache {
    /**
     * A simple shorthand for fetching the file, regardless of optimization. This works best with files that one knows the checksum of the file in question
     * @param url where the file is located on the web
     * @param checksum to verify the file that is downloaded
     * @return the file if it is found
     * @throws FileNotFoundException if the file was not found
     */
    File fetchFromInternetOrRepository(String url, String checksum) throws FileNotFoundException;

    /**
     * Preferred way of downloading files - as streams
     * @param url path to stream
     * @return inputStream
     */
    InputStream fetchAsStream(String url);

    /**
     * Verify that file is correct on disk. Used when downloading mp3's and such
     * @param pathToFileOnDisk path on disk
     * @param checksum to verify integrity of file
     * @return Whether the file exists
     */
    boolean existsInRepository(File pathToFileOnDisk, String checksum);

    /**
     * Return path to file on disk
     * @param url where the file is originally located on the web
     * @return the file itself
     */
    File fileLocation(String url);

    /**
     * Invalidates a file in the local cache
     * @param url the original url of the file
     */
    void removeFromCache(String url);

    /**
     * The mime-type of the files downloaded
     * Examples - "composition/xml", "vdl/xml"
     * @param url location of file
     * @return mime type of the file
     */
    String mimeType(String url);

    /*
     * Checks whether the downloader accepts the URL format
     */
    boolean acceptsUrl(String url);
}
