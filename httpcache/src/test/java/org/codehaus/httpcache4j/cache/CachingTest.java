package org.codehaus.httpcache4j.cache;

import com.google.common.io.Files;
import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.httpcache4j.HTTPRequest;
import org.codehaus.httpcache4j.HTTPResponse;
import org.codehaus.httpcache4j.payload.Payload;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Warning, these tests are fairly volatile and depend on one another!
 */
public class CachingTest {
    Logger log =  LoggerFactory.getLogger(getClass());
    URL returningmp3 = createURL("http://kpro09.googlecode.com/svn/test-files/holden-nothing-93_returning_mix.mp3");
    URL dvlUrl = createURL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl");


    //Pull down .vdl file from url reference
    //Read .vdl file and find where its media files, mp3's and pictures, are located
    //Check if the media files' 'md5 sums' are located already on disk in memoryCache
    //If in memoryCache use them, else download to memoryCache
    //Now, ready to be used

    HTTPRequest request = new HTTPRequest(URI.create(dvlUrl.toString()));
    VdvilHttpCache cache = VdvilHttpCache.create();

    @Test
    public void testHowCachingWorks() throws IOException {
        HTTPResponse response = cache.persistentcache.doCachedRequest(request);
        Payload payload = response.getPayload();
        assertEquals("text", payload.getMimeType().getPrimaryType());
        assertEquals("plain", payload.getMimeType().getSubType());

        BufferedReader in = new BufferedReader(new InputStreamReader(payload.getInputStream()));
    }


    @Test
    public void generateUrlChecksum() {
        String uriHex = DigestUtils.md5Hex(dvlUrl.toString());
        assertEquals("b45c6b4bd38020da03afe1f2514a3ba1", uriHex);
    }

    @Test
    public void generateMD5ChecksumFromFile() throws IOException {
        assertEquals("2e24054eb28edd38c9a846022587955b", DigestUtils.md5Hex(Files.toByteArray(new File("/tmp/vdvil/files/b45c6b4bd38020da03afe1f2514a3ba1/default"))));
    }

    @Test
    public void doesFileAlreadyExistInCache() throws FileNotFoundException {
        log.info("first");
        cache.fetchAsStream(returningmp3);
        log.info("Downloaded 1 file");
        cache.fetchAsStream(returningmp3);

        log.info("Downloaded 2 file");
        cache.fetchAsStream(returningmp3);

        log.info("Downloaded 3 file");
        cache.fetchAsStream(returningmp3);

        log.info("Downloaded 4 file");
        cache.fetchAsStream(returningmp3);

        log.info("Downloaded 5 file");
    }

    @Test
    public void validateChecksumOfLocalFiles() {
        assertFalse(cache.validateChecksum(cache.fileLocation(dvlUrl), "not the correct hex checksum"));
        assertTrue(cache.validateChecksum(cache.fileLocation(dvlUrl), "2e24054eb28edd38c9a846022587955b"));
    }

    private static URL createURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("This should never happen!", e);
        }
    }
}
