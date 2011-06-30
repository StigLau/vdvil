package no.lau.vdvil.cache;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimpleCacheTest {

    URL returningMp3;
    String returningChecksum = "e6746326540c2f847b054bb329a5d54a";
    URL testFile;
    URL ftpFile;

    SimpleCacheImpl cacheImpl = new SimpleCacheImpl();
    private URL asFile;

    @Before
    public void setup() throws MalformedURLException {
        returningMp3 = new URL("HTTP://kpro09.googlecode.com/svn/test-files/holden-nothing-93_returning_mix.mp3");
        asFile = new URL("file://localhost/tmp/holden-nothing-93_returning_mix.mp3");
        testFile = new URL("file://tmp/vdvil");
        ftpFile = new URL("ftp://not.supported");
    }

    @Test
    public void acceptsTest() {
        assertTrue(cacheImpl.accepts(returningMp3));
        assertTrue(cacheImpl.accepts(testFile));
        assertFalse(cacheImpl.accepts(ftpFile));
    }

    @Test
    public void cleanCacheTest() throws IOException {
        assertTrue(cacheImpl.removeFromCache(asFile));
        cacheImpl.fetchFromInternetOrRepository(asFile, returningChecksum);
    }

    @Test
    public void cacheTest() throws IOException {
        cacheImpl.fetchFromInternetOrRepository(asFile, returningChecksum);
    }

    @Test
    public void refreshingCacheWorks() throws IOException {
        cacheImpl.setRefreshCache(true);
        cacheImpl.fetchFromInternetOrRepository(asFile, returningChecksum);
        cacheImpl.setRefreshCache(false);
        cacheImpl.fetchFromInternetOrRepository(asFile, returningChecksum);
    }
}
