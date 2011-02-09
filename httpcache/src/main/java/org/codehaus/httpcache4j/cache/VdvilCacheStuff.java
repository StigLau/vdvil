package org.codehaus.httpcache4j.cache;

import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.httpcache4j.HTTPRequest;
import org.codehaus.httpcache4j.HTTPResponse;
import org.codehaus.httpcache4j.client.HTTPClientResponseResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;

/**
 * Wrapper class around HttpCache4J to make it more usable for VDvil usage
 * TODO Make function for invalidatingAllCaches or a single .mp3 file!!!!
 * TODO Should not be static and implement an interface!
 */
public class VdvilCacheStuff {

    static Logger log =  LoggerFactory.getLogger(VdvilCacheStuff.class);
    static String storeLocation = "/tmp/vdvil";
    static HTTPCache persistentcache = new HTTPCache(new PersistentCacheStorage(1000, new File(storeLocation), "vaudeville"), HTTPClientResponseResolver.createMultithreadedInstance());

    /**
     * A shorthand for fetching files if they have been downloaded to disk
     * Used by testing purposes
     *
     * @param url to the file
     * @return the file or null if empty
     */
    public static File fetchFromInternetOrRepository(String url, String checksum) throws FileNotFoundException {
        fetchAsStream(url);
        File locationOnDisk = fileLocation(url);
        if(existsInRepository(locationOnDisk, checksum))
            return locationOnDisk;
        else
            throw new FileNotFoundException(url + " could not be downloaded and retrieved in repository");
    }

    /**
     * @param url location of file to download
     * @return the file or null if file not found. Not a good thing to do!
     * @throws java.io.FileNotFoundException Could not find file
     */
    public static InputStream fetchAsStream(String url) throws FileNotFoundException {
        File locationOnDisk = fileLocation(url);
        if(locationOnDisk.exists() && locationOnDisk.canRead()) {
            log.debug(url + " File already in cache: " + locationOnDisk.getAbsolutePath());
            return new FileInputStream(locationOnDisk);
        }
        else {
            log.info("Did not find " + url + " in cache, downloading");
            return download(url).getPayload().getInputStream();
        }
    }

    public static File fileLocation(String url) {
        String urlChecksum = DigestUtils.md5Hex(url);
        return new File(storeLocation + "/files/" + urlChecksum + "/default");
    }

    public static boolean existsInRepository(File locationOnDisk, String checksum) {
        return locationOnDisk.exists() && locationOnDisk.canRead() && validateChecksum(locationOnDisk, checksum);
    }

    private static HTTPResponse download(String url) {
        log.info("Downloading from " +  url + " to cache: " + url);
        HTTPRequest fileRequest = new HTTPRequest(URI.create(url));
        HTTPResponse fileResponse = persistentcache.doCachedRequest(fileRequest);
        if(null != fileResponse.getETag()) {
            log.debug("ET Tag description " + fileResponse.getETag().getDescription());
        } else {
            log.error(url + " missing ET Tag");
        }
        return fileResponse;
    }

    /**
     * Calculates the checksum of the Url to find where it is located in cache, then validates the file on disk with the checksum
     * @param file location of the file to verify
     * @param checksum to check the file with
     * @return whether the file validates with the checksum
     */
    static boolean validateChecksum(File file, String checksum) {
        try {
            String fileChecksum = DigestUtils.md5Hex(new FileInputStream(file));
            if(fileChecksum.equals(checksum)) {
                log.debug("Checksum of file on disk matched the provided checksum");
                return true;
            } else {
                log.error("Checksums did not match, expected {}, was {}", checksum, fileChecksum);
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }
}
