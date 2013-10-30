package no.lau.vdvil.cache;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CacheMetaDataStorageAndLookupTest {
    String file = "/tmp/vdvil/test/cacheMetaDataStorage.ser";

    @Test
    public void testSerializingAndDeserializing() throws MalformedURLException {
        CacheMetadataStorageAndLookup storageAndLookup = new CacheMetadataStorageAndLookup(file);

        URL remote1 = new URL("http://test.com/Woopie");
        URL remote2 = new URL("http://test.com/another");
        //Delete existing cache storage file
        storageAndLookup.purgeSerializedFileOnDisk();
        //Create some data
        CacheMetaData cacheMetaData1 = storageAndLookup.putRemoteURL(remote1);
        CacheMetaData cacheMetadata2 = storageAndLookup.putRemoteURL(remote2);
        assertEquals(remote1, storageAndLookup.findById(cacheMetaData1.id).remoteAddress);
        assertEquals(cacheMetadata2, storageAndLookup.findByRemoteURL(remote2));
        //Test saving
        storageAndLookup.save();
        //Clear in-memory
        storageAndLookup.purgeInMemoryStorage();
        assertEquals(0, storageAndLookup.urlCacheMetaDataLookup.size());
        assertEquals(0, storageAndLookup.idCacheMetaDataLookup.size());
        //Test loading
        storageAndLookup.load();
        assertEquals(remote2, storageAndLookup.findById(cacheMetadata2.id).remoteAddress);
        assertEquals(cacheMetaData1.cacheId(), storageAndLookup.findByRemoteURL(remote1).cacheId());
    }

    @Test
    public void testMultipleSaves() throws MalformedURLException {
        URL remote1 = new URL("http://test.com/Woopie");
        URL url2 = new URL("http://test.com/another");
        CacheMetadataStorageAndLookup storageAndLookup = new CacheMetadataStorageAndLookup(file);
        storageAndLookup.purgeSerializedFileOnDisk();
        CacheMetaData id1 = storageAndLookup.putRemoteURL(remote1);
        storageAndLookup.save();
        storageAndLookup.putRemoteURL(url2);
        storageAndLookup.save();
        storageAndLookup.purgeInMemoryStorage();
        storageAndLookup.load();
        assertEquals(2, storageAndLookup.idCacheMetaDataLookup.size());
        assertEquals(remote1, storageAndLookup.findById(id1.id).remoteAddress);
    }

    @Test
    public void multiplePurgingsIsOk() {
        CacheMetadataStorageAndLookup storageAndLookup = new CacheMetadataStorageAndLookup(file);
        assertTrue(storageAndLookup.purgeSerializedFileOnDisk());
        assertTrue(storageAndLookup.purgeSerializedFileOnDisk());
    }
}
