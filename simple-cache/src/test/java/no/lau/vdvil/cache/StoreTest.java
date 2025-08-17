package no.lau.vdvil.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Stig@Lau.no
 * @since  10/27/13
 */
@Tag("IntegrationTest")
public class StoreTest {
    final String returningMp3 = "https://s3.amazonaws.com/dvl-test-music/music/holden-nothing-93_returning_mix.mp3";
    final String returningMp3Checksum = "3e3477a6ccba67aa9f3196390f48b67d";
    final String psylteDVL = "https://s3.amazonaws.com/dvl-test-music/dvl/loaderror-psylteflesk.dvl";
    final String psylteDVLChecksum = "fe002d377092e28afdbf25ca1eeba915";


    Store store;
    @BeforeEach
    public void setUp() {
        store = new Store();
        store.addTransport(new SimpleCacheImpl());
    }

    @Test
    public void cacheFile() throws IOException {
        StoreTest.class.getClassLoader();
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
        URL testFileUrl = ClassLoader.getSystemResource("empty_testfile.txt");
        FileRepresentation fileRepresentation = store.cache(testFileUrl, "44edc5da79b289f81094d8d5952efde7");
        // Compute expected path dynamically based on URL hash instead of hardcoding
        String expectedPath = store.getCacheMetadataStorageAndLookup().fileLocation(testFileUrl).getAbsolutePath();
        assertEquals(expectedPath, fileRepresentation.localStorage().getAbsolutePath());
    }

    @Test
    public void doesMd5SumsWork() throws IOException {
        FileRepresentation cachedFileRepresentation = store.cache(store.createKey(returningMp3, returningMp3Checksum));
        // Compute expected path dynamically instead of hardcoding
        String expectedPath = store.getCacheMetadataStorageAndLookup().fileLocation(new URL(returningMp3)).toString();
        assertEquals(expectedPath, cachedFileRepresentation.localStorage().toString());
    }

    @Test
    public void theDifferentPathsOfStoreCache() {
        int randomnr = new Random().nextInt();
        UnknownHostException thrown = Assertions.assertThrows(UnknownHostException.class, () -> {
            store.cache(new URL("https://" + randomnr + "ringadingadingading.com"));
        });
        assertEquals(randomnr + "ringadingadingading.com", thrown.getMessage());
    }

    @Test
    public void downloadSomethingWithWrongChecksum() {
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
        URL psylteUrl = new URL(psylteDVL);
        FileRepresentation fileRepresentation = store.cache(new CacheMetaData(psylteUrl, psylteDVLChecksum));
        // Compute expected path dynamically instead of hardcoding
        String expectedPath = store.getCacheMetadataStorageAndLookup().fileLocation(psylteUrl).getAbsolutePath();
        assertEquals(expectedPath, fileRepresentation.localStorage().getAbsolutePath());
        assertEquals("fe002d377092e28afdbf25ca1eeba915", fileRepresentation.md5CheckSum());
    }
}
