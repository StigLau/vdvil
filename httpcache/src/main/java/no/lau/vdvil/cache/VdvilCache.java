package no.lau.vdvil.cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
    File fetchFromInternetOrRepository(URL url, String checksum) throws IOException;

    /**
     * Preferred way of downloading files - as streams
     * @param url path to stream
     * @return inputStream
     */
    InputStream fetchAsStream(URL url);

    /**
     * Invalidates a file in the local cache
     * @param url the original url of the file
     */
    //void removeFromCache(String url);

    /**
     * The mime-type of the files downloaded
     * Examples - "composition/xml", "vdl/xml"
     * @param url location of file
     * @return mime type of the file
     */
    String mimeType(URL url);

    /*
     * Checks whether the downloader accepts the URL format
     */
    boolean accepts(URL url);
}
