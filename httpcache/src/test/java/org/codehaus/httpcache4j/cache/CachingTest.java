package org.codehaus.httpcache4j.cache;

import com.google.common.io.Files;
import com.thoughtworks.xstream.XStream;
import no.lau.tagger.model.Segment;
import no.lau.tagger.model.SimpleSong;
import org.apache.commons.codec.digest.DigestUtils;
import org.codehaus.httpcache4j.HTTPRequest;
import org.codehaus.httpcache4j.HTTPResponse;
import org.codehaus.httpcache4j.payload.Payload;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.net.URI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    XStream xstream;
    HTTPRequest request = new HTTPRequest(URI.create(dvlUrl));
    VdvilCacheStuff cache = new VdvilCacheStuff(new File("/tmp/vdvil"));

    @Before
    public void before() {
        xstream = new XStream();
        xstream.alias("track", SimpleSong.class);
        xstream.alias("segment", Segment.class);
    }

    @Test
    public void testHowCachingWorks() throws IOException {
        HTTPResponse response = cache.persistentcache.doCachedRequest(request);
        Payload payload = response.getPayload();
        assertEquals("text", payload.getMimeType().getPrimaryType());
        assertEquals("plain", payload.getMimeType().getSubType());

        BufferedReader in = new BufferedReader(new InputStreamReader(payload.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }

    @Test
    public void parsingTheXmlFromStream() {
        HTTPResponse response = cache.persistentcache.doCachedRequest(request);
        InputStream inputStream = response.getPayload().getInputStream();

        SimpleSong ss = (SimpleSong) xstream.fromXML(inputStream);
        assertEquals(new Float(130.0), ss.bpm);
        assertEquals("http://kpro09.googlecode.com/svn/test-files/holden-nothing-93_returning_mix.mp3", ss.mediaFile.fileName);
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
        System.out.println("first");
        String url = "http://kpro09.googlecode.com/svn/test-files/holden-nothing-93_returning_mix.mp3";
        cache.fetchAsStream(url);
        System.out.println("Downloaded 1 file");
        cache.fetchAsStream(url);

        System.out.println("Downloaded 2 file");
        cache.fetchAsStream(url);

        System.out.println("Downloaded 3 file");
        cache.fetchAsStream(url);

        System.out.println("Downloaded 4 file");
        cache.fetchAsStream(url);

        System.out.println("Downloaded 5 file");
    }

    @Test
    public void loadingFromDiskWithoutDownloading() throws Exception {
        SimpleSong ss = (SimpleSong) xstream.fromXML(cache.fetchAsStream(dvlUrl));
        //Retrieve mp3 file
        assertTrue("Either a seperate test has not been run that downloads the file, or something is wrong with the caching mekanism", cache.existsInRepository(ss.mediaFile.fileName));
        File file = cache.fetchFromRepository(ss.mediaFile.fileName);
        assertEquals("/tmp/vdvil/files/cab1562d1198804b5fb6d62a69004488/default", file.getAbsolutePath());
    }

    @Test
    public void validateChecksumOfLocalFiles() {
        String url = dvlUrl;
        assertEquals(false, cache.validateChecksum(url, "not the correct hex checksum"));
        assertEquals(true, cache.validateChecksum(url, "a2dc2ed2e87c74897e0a7bd7160fd810"));
    }
}
