package no.lau.vdvil.cache;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @author Stig@Lau.no
 * @since  10/27/13
 */

public class StoreTest {
    String returningMp3 = "https://s3.amazonaws.com/dvl-test-music/music/holden-nothing-93_returning_mix.mp3";
    String returningMp3Checksum = "3e3477a6ccba67aa9f3196390f48b67d";
    String psylteDVL = "https://s3.amazonaws.com/dvl-test-music/dvl/loaderror-psylteflesk.dvl";
    String psylteDVLChecksum = "fe002d377092e28afdbf25ca1eeba915";


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
        assertEquals("/tmp/vdvil/files/c28cbd3b6c75c0aa4dc6a4ff4b7c5bf5/default", fileRepresentation.localStorage().getAbsolutePath());
    }

    @Test
    public void doesMd5SumsWork() throws IOException {
        FileRepresentation cachedFileRepresentation = store.cache(store.createKey(returningMp3, returningMp3Checksum));
        assertEquals("/tmp/vdvil/files/8020a9194279b68610499dfbc0d4619a/default", cachedFileRepresentation.localStorage().toString());
    }

    @Test(expected = UnknownHostException.class)
    public void theDifferentPathsOfStoreCache() throws IOException {
        int randomnr = new Random().nextInt();
        store.cache(new URL("https://" + randomnr + "ringadingadingading.com"));
    }

    @Test
    public void downloadSomethingWithWrongChecksum() throws IOException {
        try {
            store.cache(new URL(psylteDVL), "jalla balla");
        } catch (IOException e) {
            assertEquals("No more download retries left for " + psylteDVL, e.getMessage());
            return;
        }
        fail("Should have thrown IOException because of exhausted retries");

    }

    @Test
    public void downloadSomethingWithCorrectChecksum() throws IOException {
        FileRepresentation fileRepresentation = store.cache(new CacheMetaData(new URL(psylteDVL), psylteDVLChecksum));
        assertEquals("/tmp/vdvil/files/0f6169eb434aa02b2f79929aabba1d9e/default", fileRepresentation.localStorage().getAbsolutePath());
        assertEquals("fe002d377092e28afdbf25ca1eeba915", fileRepresentation.md5CheckSum());
    }
}
