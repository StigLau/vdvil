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
    File serializedFile;
    Logger log = LoggerFactory.getLogger(getClass());

    public CacheMetadataStorageAndLookup(String filePath) {
        createFile(filePath);
    }

    private void createFile(String filePath) {
        this.serializedFile = new File(filePath);
        if(!serializedFile.exists())
            if(!serializedFile.mkdirs()) //Create file + path
                throw new RuntimeException("Unable to create Storage Cache Metadatafile at " + filePath);

    }

    /**
     * Just makes sure that there is no file on disk.
     * @return false if something bad happened
     */
    boolean purgeSerializedFileOnDisk() {
        if(serializedFile.exists()) { //Make sure that we don't try to delete an non-existing file
            if(!serializedFile.delete()){
                log.error("Purging Cache metadata file {} on disk failed.", serializedFile.getName());
                return false;
            }
        }
        return true;
    }

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

    void save() {
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(new FileOutputStream(serializedFile));
            out.writeObject(asCacheMetadataList());
            out.close();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CacheMetaData[] asCacheMetadataList() {
        List<CacheMetaData> cacheMetaDataList = new ArrayList<CacheMetaData>();
        for (CacheMetaData metaData : idCacheMetaDataLookup.values()) {
            cacheMetaDataList.add(metaData);
        }
        return cacheMetaDataList.toArray(new CacheMetaData[cacheMetaDataList.size()]);
    }

    void load() {
        ObjectInputStream in;
        CacheMetaData[] cacheMetaDatas = null;
        try {
            in = new ObjectInputStream(new FileInputStream(serializedFile));
            cacheMetaDatas = (CacheMetaData[]) in.readObject();
        } catch (Exception e) {
            throw new RuntimeException("Problem loading Caching Metadata file " + serializedFile.getName());
        }
        for (CacheMetaData cacheMetaData : cacheMetaDatas) {
            idCacheMetaDataLookup.put(cacheMetaData.id, cacheMetaData);
            urlCacheMetaDataLookup.put(cacheMetaData.remoteAddress, cacheMetaData);
        }
    }

    public void purgeInMemoryStorage() {
        idCacheMetaDataLookup.clear();
        urlCacheMetaDataLookup.clear();
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
}

