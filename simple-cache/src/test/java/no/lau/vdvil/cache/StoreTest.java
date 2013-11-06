package no.lau.vdvil.cache;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.*;

/**
 * @author Stig@Lau.no
 * @since  10/27/13
 */

public class StoreTest {
    Store store;
    @Before
    public void setUp() {
        store = new Store("/tmp/vdvil/test/cacheMetaDataStorage.ser");
        store.addTransport(new SimpleCacheImpl());
        store.purgeMetadataCache();
    }

    @Test
    public void cacheFile() {
        URL testFile = ClassLoader.getSystemResource("empty_testfile.txt");
        store.cache(testFile);
    }

    @Test
    public void findFileThatAlreadyExistInCache() {
        assertFalse(store.fileExistsInLocalCache(store.createKey("file://non-existing/file")));
        assertTrue(store.fileExistsInLocalCache(store.cache(ClassLoader.getSystemResource("empty_testfile.txt"))));
    }

    @Test
    public void findAlreadyStoredCachedFiles() {
        store.purgeMetadataCache();
        store.cache(ClassLoader.getSystemResource("empty_testfile.txt"));
    }

    @Test
    public void doesMd5SumsWork() {
        //Optional verify checksum
        String returningMp3 = "http://kpro09.googlecode.com/svn/test-files/holden-nothing-93_returning_mix.mp3";
        String md5Sum = "3e3477a6ccba67aa9f3196390f48b67d";
        FileRepresentation fr = store.createKey(returningMp3, md5Sum);
        //Legge til md5 sum
        FileRepresentation cachedFileRepresentation = store.cache(fr);
        assertEquals("file:/tmp/vdvil/files/cab1562d1198804b5fb6d62a69004488/default", cachedFileRepresentation.localStorage().toString());
    }

    @Test
    @Ignore //TODO Create this great test!
    public void theDifferentPathsOfStoreCache() {
        fail();
    }
}
