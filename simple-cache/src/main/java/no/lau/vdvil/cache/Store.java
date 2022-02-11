package no.lau.vdvil.cache;

import no.lau.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static no.lau.NullChecker.nullChecked;

/**
 * @author Stig@Lau.no
 * @since 10/27/13
 */
public class Store {

    final List<SimpleVdvilCache> transports = new ArrayList<>();
    final Logger log = LoggerFactory.getLogger(getClass());

    //Main storage mechanism against file for metadata
    final CacheMetadataStorageAndLookup cacheMetadataStorageAndLookup;

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

    public FileRepresentation createKey(String remoteURL, String checksum) {
        try {
            return createKey(new URL(remoteURL), checksum);
        } catch (MalformedURLException e) {
            log.error("Erronous URL: {}", remoteURL, e);
            return FileRepresentation.NULL;
        }
    }

    public FileRepresentation createKey(URL remoteURL, String checksum) {
        return cacheMetadataStorageAndLookup.putRemoteURL(remoteURL, checksum);
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

    public FileRepresentation cache(URL remoteURL) throws IOException {
            return cache(cacheMetadataStorageAndLookup.findByRemoteURL(nullChecked(remoteURL)));
    }

    /**
     * Shorthand without using fileRepresentation
     */
    public FileRepresentation cache(URL remoteURL, String checksum) throws IOException {
            return this.cache(createKey(nullChecked(remoteURL), checksum));
    }

    /**
     * Assures that a remote file is cached good enough in the local cache.
     * Verifies the sanity of local file with Md5 Checksum if possible
     */
    public FileRepresentation cache(FileRepresentation fileRepresentation) throws IOException {
        if (fileRepresentation == null) {
            throw new NullPointerException("FileRepresentation is NULL");
        } else if (fileRepresentation == FileRepresentation.NULL) {
            log.warn("Store was asked to cache FileRepresentation.NULL");
            return fileRepresentation;
        } else if (fileRepresentation.remoteAddress() == null) {
            throw new RuntimeException("Remote URL Required");
        } else {
            if (fileRepresentation.localStorage() != null) {
                if (fileRepresentation.md5CheckSum() == null) {
                    log.info("The file {} is confirmed in cache but has no MD5 checksum", fileRepresentation.remoteAddress());
                    return fileRepresentation;
                } else {
                    String fileChecksum = MD5.md5Hex(Files.readAllBytes(Paths.get(fileRepresentation.localStorage().toURI())));
                    if (fileChecksum.equals(fileRepresentation.md5CheckSum())) {
                        log.debug("Checksum of {} confirmed", fileRepresentation);
                        return fileRepresentation;
                    } else {
                        log.error("Checksum NOT confirmed for '{}'. Expected checksum '{}', download was '{}'. Retrying download in hope that a fresh download fixes the problem", fileRepresentation.remoteAddress(), fileRepresentation.md5CheckSum(), fileChecksum);
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
            throw new FileNotFoundException("No more download retries left for " + fileRepresentation.remoteAddress());
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
