package no.lau.vdvil.cache;

import org.junit.Before;
import org.junit.Test;
import java.net.MalformedURLException;
import java.net.URL;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimpleCacheTest {

    URL localTestFile;
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
}
