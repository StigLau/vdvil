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
        assertFalse(store.fileExistsInLocalCache(store.createKey("file://non-existing/file")));
        assertTrue(store.fileExistsInLocalCache(store.cache(ClassLoader.getSystemResource("empty_testfile.txt"))));
    }

    @Test
    public void findAlreadyStoredCachedFiles() throws IOException {
        store.cache(ClassLoader.getSystemResource("empty_testfile.txt"));
        //TODO Check that the local adress in cache is added
    }

    @Test
    public void doesMd5SumsWork() throws IOException {
        FileRepresentation fr = store.createKey(returningMp3, returningMp3Checksum);
        FileRepresentation cachedFileRepresentation = store.cache(fr);
        assertEquals("/tmp/vdvil/files/cab1562d1198804b5fb6d62a69004488/default", cachedFileRepresentation.localStorage().toString());
    }

    @Test(expected = UnknownHostException.class)
    public void theDifferentPathsOfStoreCache() throws IOException {
        store.cache(new CacheMetaData(){{
            remoteAddress = new URL("http://123ringadingadingading.com");
            downloadAttempts = 1;
        }});
    }

    @Test
    public void downloadSomethingWithWrongChecksum() throws IOException {
        try {
            store.cache(new CacheMetaData(){{
                remoteAddress = new URL(psylteDVL);
                downloadAttempts = 1;
                md5CheckSum = "jalla balla";
            }});
        } catch (IOException e) {
            assertEquals("No more download retries left for http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/loaderror-psylteflesk.dvl", e.getMessage());
            return;
        }
        fail("Should have thrown IOException because of exhausted retries");

    }

    @Test
    public void downloadSomethingWithCorrectChecksum() throws IOException {
        store.cache(new CacheMetaData(){{
            remoteAddress = new URL(psylteDVL);
            downloadAttempts = 1;
            md5CheckSum = psylteDVLChecksum;
        }});
    }
}
