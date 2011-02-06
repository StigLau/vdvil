package org.codehaus.httpcache4j.cache;

import no.lau.vdvil.common.VdvilFileCache;
import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.httpcache4j.HTTPRequest;
import org.codehaus.httpcache4j.HTTPResponse;
import org.codehaus.httpcache4j.client.HTTPClientResponseResolver;
import org.codehaus.httpcache4j.payload.Payload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;

/**
 * Wrapper class around HttpCache4J to make it more usable for VDvil usage
 * TODO Make function for invalidatingAllCaches
 */
public class VdvilCacheStuff implements VdvilFileCache {

    final Logger log =  LoggerFactory.getLogger(VdvilCacheStuff.class);
    public final HTTPCache persistentcache;
    final File storeLocation;
    final String storeName = "vaudeville";

    /**
     * @param location Where to persist the cache files
     */
    public VdvilCacheStuff(File location) {
        storeLocation = location;
        persistentcache = new HTTPCache(new PersistentCacheStorage(1000, location, storeName), HTTPClientResponseResolver.createMultithreadedInstance());
    }

    /**
     * First performs a validation. Then, tries to download from local file repo before trying to download from the web
     * If the file was downloaded from the internet, perform a second validation to confirm that the file was downloaded successfully
     * @param url location of file to download
     * @param checksum to validate against
     * @return the downloaded file, null if not found
     * @throws java.io.FileNotFoundException if the file could not be downloaded from url of found in cache
     */
    public InputStream fetchAsStream(String url, String checksum) throws FileNotFoundException {
        if (validateChecksum(url, checksum)) {
            log.error("{} located on disk with correct checksum", url);
            return fetchAsStream(url);
        } else {
            log.info("File missing in cache or failed to pass checksum. Retrying downloading from URL");
            InputStream downloadedFromTheInternet = download(url).getPayload().getInputStream();
            if(validateChecksum(url, checksum)) {
                return downloadedFromTheInternet;
            } else {
                throw new FileNotFoundException("Could not download " + url + " to cache. File in cache failed validation with checksum " + checksum);
            }
        }
    }

    /**
     * @param url location of file to download
     * @return the file or null if file not found. Not a good thing to do!
     * @throws java.io.FileNotFoundException Could not find file
     */
    public InputStream fetchAsStream(String url) throws FileNotFoundException {
        if(existsInRepository(url)) {
            File fileInRepository = fileLocation(url);
            log.info(url + " File already in cache: " + fileInRepository.getAbsolutePath());
            return new FileInputStream(fileInRepository);
        }
        else {
            return download(url).getPayload().getInputStream();
        }
    }

    private HTTPResponse download(String url) {
        log.info("Downloading from" +  url + " to cache: " + url);
        HTTPRequest fileRequest = new HTTPRequest(URI.create(url));
        HTTPResponse fileResponse = persistentcache.doCachedRequest(fileRequest);
        if(null != fileResponse.getETag()) {
            log.debug("ET Tag description " + fileResponse.getETag().getDescription());
        } else {
            log.error(url + " missing ET Tag");
        }
        return fileResponse;
    }

    File fileLocation(String url) {
        String urlChecksum = DigestUtils.md5Hex(url);
        return new File(storeLocation + "/files/" + urlChecksum + "/default");
    }

    public boolean existsInRepository(String url) {
        File locationOnDisk = fileLocation(url);
        return locationOnDisk.exists() && locationOnDisk.canRead();
    }

    public boolean existsInRepository(String url, String checksum) {
        return existsInRepository(url) && validateChecksum(url, checksum);
    }

    /**
     * A shorthand for fetching files if they have been downloaded to disk
     *
     * @param url to the file
     * @return the file or null if empty
     */
    public File fetchFromRepository(String url) {
        if(existsInRepository(url)) {
            return fileLocation(url);
        } else {
            log.error("File not found at {} in local repository", fileLocation(url).getAbsolutePath());
            return null;
        }
    }

    /**
     * Calculates the checksum of the Url to find where it is located in cache, then validates the file on disk with the checksum
     * @param url Where the file is located on the web
     * @param checksum to check the file with
     * @return whether the file validates with the checksum
     */
    boolean validateChecksum(String url, String checksum) {
        try {
            String fileChecksum = DigestUtils.md5Hex(new FileInputStream(fileLocation(url)));
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
