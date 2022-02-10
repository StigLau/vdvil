package no.lau.vdvil.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.MalformedURLException;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleCacheTest {

    URL localTestFile;
    URL testFile;
    URL ftpFile;

    final SimpleCacheImpl cacheImpl = new SimpleCacheImpl();

    @BeforeEach
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
