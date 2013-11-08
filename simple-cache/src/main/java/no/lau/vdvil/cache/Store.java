package no.lau.vdvil.cache;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
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

    Store() {
        cacheMetadataStorageAndLookup = new CacheMetadataStorageAndLookup();
    }

    /**
     * The preffered way of constructing a Store. A store is a singleton
     * Todo - choose downloaders by .properties file
     */
    public static Store get() {
        if (store == null) {
            store = new Store();
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
            if (transport.accepts(fileRepresentation.localStorage().toURL())) {
                try {
                    return ((VdvilCache)transport).fetchAsStream(fileRepresentation.localStorage().toURL());
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

    public void download(URL url, File localStorage) throws IOException {
        for (SimpleVdvilCache cache : transports) {
            if (cache instanceof VdvilCache) {
                ((VdvilCache) cache).fetchFromInternet(url, localStorage);
                return;
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

    public FileRepresentation cache(URL remoteURL) throws IOException {
        if(remoteURL == null)
            throw new RuntimeException("Cannot cache NULL URL");
        else
            return cache(cacheMetadataStorageAndLookup.findByRemoteURL(remoteURL));
    }
    /**
     * Assures that a remote file is cached good enough in the local cache.
     * Verifies the sanity of local file with Md5 Checksum if possible
     */
    public FileRepresentation cache(FileRepresentation fileRepresentation) throws IOException {
        if (fileRepresentation == null) {
            throw new RuntimeException("File with ID " + fileRepresentation + " does not exist in cache.");
        } else if (fileRepresentation == FileRepresentation.NULL) {
            log.warn("Store was asked to cache FileRepresentation.NULL");
            return fileRepresentation;
        } else if (fileRepresentation.remoteAddress() == null) {
            throw new RuntimeException("Remote URL Required");
        } else {
            if (fileRepresentation.localStorage() != null) {
                if (fileRepresentation.md5CheckSum() == null) {
                    log.info("The file {} is confirmed in cache but has no MD5 checksum", fileRepresentation);
                    return fileRepresentation;
                } else {
                    String fileChecksum = DigestUtils.md5Hex(new FileInputStream(fileRepresentation.localStorage()));
                    if (fileChecksum.equals(fileRepresentation.md5CheckSum())) {
                        log.debug("Checksum of {} confirmed", fileRepresentation);
                        return fileRepresentation;
                    } else {
                        log.error("Checksum of {} NOT confirmed. File checksum was {}. Retrying download in hope that a fresh download fixes the problem", fileRepresentation, fileChecksum);
                        CacheMetaData mutable = cacheMetadataStorageAndLookup.mutableFileRepresentation(fileRepresentation);
                        mutable.localStorage = null;
                        return downloadFile(mutable);
                    }
                }
            } else {
                //Find local cache location
                File cacheLocation = cacheMetadataStorageAndLookup.fileLocation(fileRepresentation.remoteAddress());
                if (cacheLocation.exists() && cacheLocation.canRead()) {
                    CacheMetaData mutable = cacheMetadataStorageAndLookup.mutableFileRepresentation(fileRepresentation);

                    log.debug("Found a cached copy of {} at {}", fileRepresentation.remoteAddress(), fileRepresentation.localStorage());
                    mutable.localStorage = cacheLocation;
                    return cache(mutable);
                } else {
                    //Download the file and try again
                    return downloadFile(fileRepresentation);
                }
            }
        }
    }

    private FileRepresentation downloadFile(FileRepresentation fileRepresentation) throws IOException {
        CacheMetaData mutable = cacheMetadataStorageAndLookup.mutableFileRepresentation(fileRepresentation);

        if (fileRepresentation.downloadAttemptsLeft() > 0) {
            mutable.downloadAttempts--;
            mutable.localStorage = cacheMetadataStorageAndLookup.fileLocation(fileRepresentation.remoteAddress());
            download(fileRepresentation.remoteAddress(), mutable.localStorage);
            //Retry the cache again to check that everything is ok!
            return cache(mutable);
        } else {
            throw new IOException("No more download retries left for " + fileRepresentation.remoteAddress());
        }
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

    boolean fileExistsInLocalCache(FileRepresentation fileRepresentation) {
        //Where should the file be placed
        File fileLocation = cacheMetadataStorageAndLookup.fileLocation(fileRepresentation.remoteAddress());
        return fileLocation.exists();
    }
}
