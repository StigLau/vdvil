package no.lau.vdvil.cache;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author Stig@Lau.no
 * @since  10/27/13
 */
public class CacheMetadataStorageAndLookup implements Serializable{
    final Map<URL, CacheMetaData> urlCacheMetaDataLookup = new HashMap<>();
    final static String storeLocation = "/tmp/vdvil";

    CacheMetaData putRemoteURL(final URL url, String md5Checksum) {
        //If the remote URL already exists in the cache, reuse that rather than creating a new
        if(urlCacheMetaDataLookup.containsKey(url)) {
            return urlCacheMetaDataLookup.get(url);
        } else {
            CacheMetaData cacheMetaData = new CacheMetaData(url, md5Checksum);
            urlCacheMetaDataLookup.put(url, cacheMetaData);
            return cacheMetaData;
        }
    }

    public CacheMetaData findByRemoteURL(URL url) {
        if(urlCacheMetaDataLookup.containsKey(url))
            return urlCacheMetaDataLookup.get(url);
        else
            return putRemoteURL(url, null);
    }

    /**
     * Mutability "protection", because it is seldom needed.
     */
    CacheMetaData mutableFileRepresentation(final FileRepresentation fileRepresentation) {
        return (CacheMetaData) fileRepresentation;
    }

    /**
     * Return path to file on disk
     *
     * @param url where the file is originally located on the web
     * @return the file itself
     */
    public File fileLocation(URL url) {
        String urlChecksum = DigestUtils.md5Hex(url.toString());
        return new File(storeLocation + "/files/" + urlChecksum + "/default");
    }

    public boolean removeFromCache(URL url) {
        return fileLocation(url).delete();
    }
}

