package org.codehaus.httpcache4j.cache;

import com.google.common.io.Files;
import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.httpcache4j.HTTPRequest;
import org.codehaus.httpcache4j.HTTPResponse;
import org.codehaus.httpcache4j.payload.Payload;
import org.junit.Test;
import java.io.*;
import java.net.URI;
import static org.junit.Assert.assertEquals;
import static org.codehaus.httpcache4j.cache.VdvilCacheStuff.*;

/**
 * Warning, these tests are fairly volatile and depend on one another!
 */
public class CachingTest {
    //Pull down .vdl file from url reference
    //Read .vdl file and find where its media files, mp3's and pictures, are located
    //Check if the media files' 'md5 sums' are located already on disk in memoryCache
    //If in memoryCache use them, else download to memoryCache
    //Now, ready to be used

    final String dvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl";

    HTTPRequest request = new HTTPRequest(URI.create(dvlUrl));

    @Test
    public void testHowCachingWorks() throws IOException {
        HTTPResponse response = persistentcache.doCachedRequest(request);
        Payload payload = response.getPayload();
        assertEquals("text", payload.getMimeType().getPrimaryType());
        assertEquals("plain", payload.getMimeType().getSubType());

        BufferedReader in = new BufferedReader(new InputStreamReader(payload.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            log.info(line);
        }
    }


    @Test
    public void generateUrlChecksum() {
        String uriHex = DigestUtils.md5Hex(dvlUrl);
        assertEquals("b45c6b4bd38020da03afe1f2514a3ba1", uriHex);
    }

    @Test
    public void generateMD5ChecksumFromFile() throws IOException {
        assertEquals("a2dc2ed2e87c74897e0a7bd7160fd810", DigestUtils.md5Hex(Files.toByteArray(new File("/tmp/vdvil/files/b45c6b4bd38020da03afe1f2514a3ba1/default"))));
    }

    @Test
    public void doesFileAlreadyExistInCache() throws FileNotFoundException {
        log.info("first");
        String url = "http://kpro09.googlecode.com/svn/test-files/holden-nothing-93_returning_mix.mp3";
        fetchAsStream(url);
        log.info("Downloaded 1 file");
        fetchAsStream(url);

        log.info("Downloaded 2 file");
        fetchAsStream(url);

        log.info("Downloaded 3 file");
        fetchAsStream(url);

        log.info("Downloaded 4 file");
        fetchAsStream(url);

        log.info("Downloaded 5 file");
    }

    @Test
    public void validateChecksumOfLocalFiles() {
        String url = dvlUrl;
        assertEquals(false, validateChecksum(url, "not the correct hex checksum"));
        assertEquals(true, validateChecksum(url, "a2dc2ed2e87c74897e0a7bd7160fd810"));
    }
}
