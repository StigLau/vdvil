package org.codehaus.httpcache4j.cache;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.codehaus.httpcache4j.HTTPRequest;
import org.codehaus.httpcache4j.HTTPResponse;
import org.codehaus.httpcache4j.client.HTTPClientResponseResolver;

import java.io.*;
import java.net.URI;

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
     * @param url location of file to download
     * @return the file or null if file not found. Not a good thing to do!
     */
    public File fetchAsFile(String url) {
        File fileInRepository = fetchFromRepository(url);
        if (fileInRepository != null)
            return fileInRepository;
        else {
            HTTPRequest fileRequest = new HTTPRequest(URI.create(url));
            HTTPResponse fileResponse = persistentcache.doCachedRequest(fileRequest);
            if (fileResponse.getPayload() instanceof CleanableFilePayload) {
                CleanableFilePayload cleanableFilePayload = (CleanableFilePayload) fileResponse.getPayload();
                return cleanableFilePayload.getFile();
            } else
                return null;
        }
    }

    /**
     * A shorthand for fetching files if they have been downloaded to disk
     *
     * @param url to the file
     * @return the file or null if empty
     */
    public File fetchFromRepository(String url) {
        String urlChecksum = DigestUtils.md5Hex(url);
        File locationOnDisk = new File(storeLocation + "/files/" + urlChecksum + "/default");
        if (locationOnDisk.exists() && locationOnDisk.canRead())
            return locationOnDisk;
        else
            return null;
    }
}
