package no.lau.vdvil.cache;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimpleCacheTest {

    URL localTestFile;
    String returningChecksum = "e6746326540c2f847b054bb329a5d54a";
    URL testFile;
    URL ftpFile;

    SimpleCacheImpl cacheImpl = new SimpleCacheImpl();

    @Before
    public void setup() throws MalformedURLException {
        localTestFile = getClass().getResource("/empty_testfile.txt");
        testFile = new URL("file://tmp/vdvil");
        ftpFile = new URL("ftp://not.supported");
    }

    @Test
    public void acceptsTest() {
        assertTrue(cacheImpl.accepts(localTestFile));
        assertTrue(cacheImpl.accepts(testFile));
        assertFalse(cacheImpl.accepts(ftpFile));
    }

    @Test
    public void cleanCacheTest() throws IOException {
        assertTrue(cacheImpl.removeFromCache(localTestFile));
        cacheImpl.fetchFromInternetOrRepository(localTestFile, returningChecksum);
    }

    @Test
    public void cacheTest() throws IOException {
        cacheImpl.fetchFromInternetOrRepository(localTestFile, returningChecksum);
    }

    @Test
    public void refreshingCacheWorks() throws IOException {
        cacheImpl.setRefreshCache(true);
        cacheImpl.fetchFromInternetOrRepository(localTestFile, returningChecksum);
        cacheImpl.setRefreshCache(false);
        cacheImpl.fetchFromInternetOrRepository(localTestFile, returningChecksum);
    }
}
