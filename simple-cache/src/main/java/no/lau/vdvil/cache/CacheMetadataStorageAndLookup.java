package no.lau.vdvil.cache;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author Stig@Lau.no
 * @since  10/27/13
 */
public class CacheMetadataStorageAndLookup implements Serializable{
    final Map<String, CacheMetaData> idCacheMetaDataLookup = new HashMap<String, CacheMetaData>();
    final Map<URL, CacheMetaData> urlCacheMetaDataLookup = new HashMap<URL, CacheMetaData>();
    final static String storeLocation = "/tmp/vdvil";
    Logger log = LoggerFactory.getLogger(getClass());

    CacheMetaData putRemoteURL(final URL url) {
        //If the remote URL already exists in the cache, reuse that rather than creating a new
        if(urlCacheMetaDataLookup.containsKey(url)) {
            return urlCacheMetaDataLookup.get(url);
        } else {
            final String key = DigestUtils.md5Hex(url.toString());
            CacheMetaData cacheMetaData = new CacheMetaData() {{
                id = key;
                remoteAddress = url;
            }};
            idCacheMetaDataLookup.put(key, cacheMetaData);
            urlCacheMetaDataLookup.put(url, cacheMetaData);
            return cacheMetaData;
        }
    }

    CacheMetaData findById(String id) {
        return idCacheMetaDataLookup.get(id);
    }

    public CacheMetaData findByRemoteURL(URL url) {
        if(urlCacheMetaDataLookup.containsKey(url))
            return urlCacheMetaDataLookup.get(url);
        else
            return putRemoteURL(url);
    }

    /**
     * Mutability "protection", because it is seldom needed.
     */
    public CacheMetaData mutableFileRepresentation(final FileRepresentation fileRepresentation) {
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

