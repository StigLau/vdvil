package org.codehaus.httpcache4j.cache;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.codehaus.httpcache4j.HTTPRequest;
import org.codehaus.httpcache4j.HTTPResponse;
import org.codehaus.httpcache4j.client.HTTPClientResponseResolver;

import java.io.*;
import java.net.URI;

/**
 * Wrapper class around HttpCache4J to make it more usable for VDvil usage
 */
public class VdvilCacheStuff {


    public final HTTPCache persistentcache;
    final File storeLocation;
    final String storeName = "vaudeville";

    /**
     * @param location Where to persist the cache files
     */
    public VdvilCacheStuff(File location) {
        storeLocation = location;
        persistentcache = new HTTPCache(new PersistentCacheStorage(1000, location, storeName), new HTTPClientResponseResolver(new HttpClient()));
    }

    /**
     * @param url location of stream to download
     * @return InputStream
     */
    public InputStream fetchAsInputStream(String url) {
        File fileInRepository = fetchFromRepository(url);
        if (fileInRepository != null) {
            try {
                return new FileInputStream(fileInRepository);
            } catch (FileNotFoundException e) { /* If file not found - will continue to download it */}
        }
        HTTPRequest request = new HTTPRequest(URI.create(url));
        HTTPResponse response = persistentcache.doCachedRequest(request);
        return response.getPayload().getInputStream();
    }

    /**
     * First performs a validation. Then, tries to download from local file repo before trying to download from the web
     * @param url location of file to download
     * @param checksum to validate against
     * @return the downloaded file, null if not found 
     */
    public File fetchAsFile(String url, String checksum) {
        if (validateChecksum(url, checksum)) {
            System.out.println("Checksum passed: " + url);
            return fetchAsFile(url);
        } else {
            System.out.println("File failed to pass checksum.");
            return downloadFromInternetAsFile(url);

        }
    }

    /**
     * @param url location of file to download
     * @return the file or null if file not found. Not a good thing to do!
     */
    public File fetchAsFile(String url) {
        File fileInRepository = fetchFromRepository(url);
        if (fileInRepository != null) {
            System.out.println("File already in cache: " + url);
            return fileInRepository;
        }
        else {
            return downloadFromInternetAsFile(url);
        }
    }

    File downloadFromInternetAsFile(String url) {
        System.out.println("Downloading " + url + " to cache");
        HTTPRequest fileRequest = new HTTPRequest(URI.create(url));
        HTTPResponse fileResponse = persistentcache.doCachedRequest(fileRequest);
        if (fileResponse.getPayload() instanceof CleanableFilePayload) {
            CleanableFilePayload cleanableFilePayload = (CleanableFilePayload) fileResponse.getPayload();
            return cleanableFilePayload.getFile();
        } else
            return null;
    }

    /**
     * A shorthand for fetching files if they have been downloaded to disk
     *
     * @param url to the file
     * @return the file or null if empty
     */
    File fetchFromRepository(String url) {
        String urlChecksum = DigestUtils.md5Hex(url);
        File locationOnDisk = new File(storeLocation + "/files/" + urlChecksum + "/default");
        if (locationOnDisk.exists() && locationOnDisk.canRead())
            return locationOnDisk;
        else
            return null;
    }

    boolean validateChecksum(String url, String checksum) {
        String urlChecksum = DigestUtils.md5Hex(url);
        File locationOnDisk = new File(storeLocation + "/files/" + urlChecksum + "/default");
        try {
            String fileChecksum = DigestUtils.md5Hex(new FileInputStream(locationOnDisk));
            return checksum.equals(fileChecksum);
        } catch (IOException e) {
            return false;
        }
    }
}
