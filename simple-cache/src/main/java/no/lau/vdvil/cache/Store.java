package no.lau.vdvil.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stig@Lau.no
 * @since 10/27/13
 */
public class Store {

    List<SimpleVdvilCache> transports = new ArrayList<SimpleVdvilCache>();
    Logger log = LoggerFactory.getLogger(getClass());

    //Main storage mechanism against file for metadata
    CacheMetadataStorageAndLookup cacheMetadataStorageAndLookup;

    //There is only one store
    private static Store store;

    Store(String cacheMetadataStorageLocation) {
        cacheMetadataStorageAndLookup = new CacheMetadataStorageAndLookup(cacheMetadataStorageLocation);
    }

    /**
     * The preffered way of constructing a Store. A store is a singleton
     * Todo - choose downloaders by .properties file
     */
    public static Store get() {
        if (store == null) {
            store = new Store("/tmp/vdvil/cacheMetaDataStorage.ser");
            store.addTransport(new SimpleCacheImpl());
        }
        return store;
    }


    public FileRepresentation createKey(String url) {
        try {
            return createKey(new URL(url));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public FileRepresentation createKey(String remoteURL, String checksum) {
        CacheMetaData fileRepresentation = cacheMetadataStorageAndLookup.mutableFileRepresentation(createKey(remoteURL));
        fileRepresentation.md5CheckSum = checksum;
        return fileRepresentation;
    }

    public CacheMetaData createKey(URL remoteURL) {
        return cacheMetadataStorageAndLookup.putRemoteURL(remoteURL);
    }

    @Deprecated //Please use the one with fileRepresentation!
    public InputStream fetchAsStream(String cacheId) throws IOException {
        return fetchAsStream(cacheMetadataStorageAndLookup.findById(cacheId));
    }

    public InputStream fetchAsStream(FileRepresentation fileRepresentation) throws IOException {
        for (SimpleVdvilCache transport : transports) {
            if (transport.accepts(fileRepresentation.localStorage())) {
                try {
                    return ((VdvilCache)transport).fetchAsStream(fileRepresentation.localStorage());
                } catch (IOException ioE) {
                    log.warn("DownloaderFacade {} could not fetch {}", new Object[]{transport, fileRepresentation.localStorage()}, ioE);
                }
            }
        }
        log.error("Not able to dowload or fetch {}, check debug log!", fileRepresentation.remoteAddress());
        throw new IOException("Unable to fetch " + fileRepresentation.remoteAddress() + " with any of the transports");
    }

    public void addTransport(SimpleVdvilCache cache) {
        this.transports.add(cache);
    }

    public URL fetchFromCache(URL url, String checksum) throws IOException {
        for (SimpleVdvilCache cache : transports) {
            if (cache instanceof VdvilCache) {
                VdvilCache vdvilCache = (VdvilCache) cache;
                return vdvilCache.fetchFromInternetOrRepository(url, checksum).toURI().toURL();
            }
        }
        throw new IOException("No VdvilCache configured for " + getClass().getName());
    }

    /**
     * @deprecated Please use CacheMetadata functionality!
     */
    public void setRefreshCaches(boolean value) throws IOException {
        for (SimpleVdvilCache cache : transports) {
            cache.setRefreshCache(value);
        }
    }

    public FileRepresentation cache(URL remoteURL) {
        if(remoteURL == null)
            throw new RuntimeException("Cannot cache NULL URL");
        else
            return cache(cacheMetadataStorageAndLookup.findByRemoteURL(remoteURL));
    }
    /**
     * Assures that a remote file is cached good enough in the local cache.
     * Verifies the sanity of local file with Md5 Checksum if possible
     */
    public FileRepresentation cache(FileRepresentation fileRepresentation) {
        try {
            if (fileRepresentation == null) {
                throw new RuntimeException("File with ID " + fileRepresentation + " does not exist in cache.");
            } else if (fileRepresentation == FileRepresentation.NULL) {
                log.debug("Store was asked to cache FileRepresentation.NULL");
            } else {
                CacheMetaData mutable = cacheMetadataStorageAndLookup.mutableFileRepresentation(fileRepresentation);

                if(fileRepresentation.cacheId() == null) {
                    mutable.localStorage = fetchFromCache(fileRepresentation.remoteAddress(), fileRepresentation.md5CheckSum());
                    return mutable;
                }

                if(fileRepresentation.localStorage() == null) {
                    File cacheLocation = CacheFacade.fileLocation(fileRepresentation.remoteAddress());
                    if (cacheLocation.exists()) {
                        log.debug("Found a cached copy of {} at {}", fileRepresentation.remoteAddress(), fileRepresentation.localStorage());
                        mutable.localStorage = cacheLocation.toURL();
                    }
                    //Else cache
                }

                //Check if file has been cached
                if (fileRepresentation.localStorage() != null) {
                    File file = new File(fileRepresentation.localStorage().toURI());
                    //Check if file exists locally
                    if (file.exists()) {
                        //Check md5 sum if exists
                        if (fileRepresentation.md5CheckSum() != null) {
                            //Test if md5 sum is reliable
                            for (SimpleVdvilCache transport : transports) {
                                if (transport instanceof CacheFacade) {
                                    if(((CacheFacade) transport).validateChecksum(file, fileRepresentation.md5CheckSum())) {
                                        return fileRepresentation; //Verified that sane file exists in cache. Hooray!
                                    }
                                }
                            }
                            //Retrying download of file because of erronous checksum
                            mutable.localStorage = fetchFromCache(fileRepresentation.remoteAddress(), fileRepresentation.md5CheckSum());
                            return mutable;
                        } else {
                            log.info("{} has not been verified by MD5 checksum. However, it is retrieved from cache: " + fileRepresentation.localStorage().toString(), fileRepresentation.remoteAddress().toString());
                        }
                    }
                } else { //Not found in cache metata, or the file is not realiable. Caching file
                        log.debug("Could not find a cached copy of {}, downloading from the web", fileRepresentation.remoteAddress());
                        mutable.localStorage = this.fetchFromCache(fileRepresentation.remoteAddress(), null);
                        return mutable;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Something bad happened in cache ", e);
        }
        return fileRepresentation;
    }

    /**
     * Ask if the Store with its transports accepts a URL format
     */
    public boolean accepts(URL url) {
        for (SimpleVdvilCache cache : transports) {
            if (cache.accepts(url))
                return true;
        }
        return false;
    }

    /**
     * For purging the cache Metadata. Both In Memory and File on disk
     */
    void purgeMetadataCache() {
        cacheMetadataStorageAndLookup.purgeInMemoryStorage();
        cacheMetadataStorageAndLookup.purgeSerializedFileOnDisk();
    }

    boolean fileExistsInLocalCache(FileRepresentation fileRepresentation) {
        //Where should the file be placed
        File fileLocation = CacheFacade.fileLocation(fileRepresentation.remoteAddress());
        return fileLocation.exists();
    }
}
