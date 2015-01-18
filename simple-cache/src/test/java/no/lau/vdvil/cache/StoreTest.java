package no.lau.vdvil.cache;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

/**
 * @author Stig@Lau.no
 * @since  10/27/13
 */

public class StoreTest {
    String returningMp3 = "http://kpro09.googlecode.com/svn/test-files/holden-nothing-93_returning_mix.mp3";
    String returningMp3Checksum = "3e3477a6ccba67aa9f3196390f48b67d";
    String psylteDVL = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/loaderror-psylteflesk.dvl";
    String psylteDVLChecksum = "88a5ea828b7029b3887a9ccbdf810408";


    Store store;
    @Before
    public void setUp() {
        store = new Store();
        store.addTransport(new SimpleCacheImpl());
    }

    @Test
    public void cacheFile() throws IOException {
        URL testFile = ClassLoader.getSystemResource("empty_testfile.txt");
        store.cache(testFile);
    }

    @Test
    public void findFileThatAlreadyExistInCache() throws IOException {
        assertFalse(store.fileExistsInLocalCache(store.createKey("file://non-existing/file", null)));
        assertTrue(store.fileExistsInLocalCache(store.cache(ClassLoader.getSystemResource("empty_testfile.txt"))));
    }

    @Test
    public void findAlreadyStoredCachedFiles() throws IOException {
        FileRepresentation fileRepresentation = store.cache(ClassLoader.getSystemResource("empty_testfile.txt"), "44edc5da79b289f81094d8d5952efde7");
        assertEquals("/tmp/vdvil/files/92179b233a8e682cd472b878c7da3511/default", fileRepresentation.localStorage().getAbsolutePath());
    }

    @Test
    public void doesMd5SumsWork() throws IOException {
        FileRepresentation cachedFileRepresentation = store.cache(store.createKey(returningMp3, returningMp3Checksum));
        assertEquals("/tmp/vdvil/files/cab1562d1198804b5fb6d62a69004488/default", cachedFileRepresentation.localStorage().toString());
    }

    @Test(expected = UnknownHostException.class)
    public void theDifferentPathsOfStoreCache() throws IOException {
        store.cache(new URL("http://123ringadingadingading.com"));
    }

    @Test
    public void downloadSomethingWithWrongChecksum() throws IOException {
        try {
            store.cache(new URL(psylteDVL), "jalla balla");
        } catch (IOException e) {
            assertEquals("No more download retries left for http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/loaderror-psylteflesk.dvl", e.getMessage());
            return;
        }
        fail("Should have thrown IOException because of exhausted retries");

    }

    @Test
    public void downloadSomethingWithCorrectChecksum() throws IOException {
        FileRepresentation fileRepresentation = store.cache(new CacheMetaData(new URL(psylteDVL), psylteDVLChecksum));
        assertEquals("/tmp/vdvil/files/d7aff61536968c477d1842d052afe15d/default", fileRepresentation.localStorage().getAbsolutePath());
        assertEquals("88a5ea828b7029b3887a9ccbdf810408", fileRepresentation.md5CheckSum());
    }
}
