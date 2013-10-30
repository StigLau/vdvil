package no.lau.vdvil.cache;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
    @Ignore
    public void doesMd5SumsWork() {
        fail();
    }

    @Test
    @Ignore //TODO Create this great test!
    public void theDifferentPathsOfStoreCache() {
        fail();
    }
}
