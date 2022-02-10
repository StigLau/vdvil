package no.lau.vdvil.cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * A generic interface for all transports
 */
public interface VdvilCache {
    /**
     * A simple shorthand for fetching the file, regardless of optimization. This works best with files that one knows the checksum of the file in question
     * @param url where the file is located on the web
     * @param localStorage where the file is to be stored locally
     * @throws FileNotFoundException if the file was not found
     */
    void fetchFromInternet(URL url, File localStorage) throws IOException;

    /**
     * Access to streams, bypassing cache
     * @param url path to stream
     * @return inputStream
     */
    InputStream fetchAsStream(URL url) throws IOException;

    /*
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
