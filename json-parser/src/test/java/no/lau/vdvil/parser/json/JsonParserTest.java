package no.lau.vdvil.parser.json;

import com.google.gson.Gson;
import no.lau.vdvil.cache.DownloaderFacade;
import no.vdvil.renderer.audio.MediaFile;
import no.vdvil.renderer.audio.Segment;
import no.vdvil.renderer.audio.Track;
import org.junit.Test;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class JsonParserTest {

    String testJson = "{\"reference\":\"trackReference\",\"bpm\":123.0,\"mediaFile\":{\"fileName\":\"http://url.com\",\"startingOffset\":1000.0,\"checksum\":\"a checksum\"},\"segments\":[{\"id\":\"id1\",\"text\":\"hello\",\"start\":0,\"end\":16},{\"id\":\"id2\",\"text\":\"goodbye\",\"start\":16,\"end\":32}]}";

    @Test
    public void testWritingTrackToJson() throws MalformedURLException {
        List<Segment> segments = new ArrayList<Segment>();
        segments.add(new Segment("id1", 0, 16, "hello"));
        segments.add(new Segment("id2", 16, 32, "goodbye"));

        Track track = new Track("trackReference", 123F, new MediaFile(new URL("http://url.com"), 1000F, "a checksum"), segments);
        Gson gson = new Gson();
        String jsonResult = gson.toJson(track);
        System.out.println(jsonResult);
        assertEquals(testJson, jsonResult);
    }

    @Test
    public void testParsingJson() throws MalformedURLException {
        DownloaderFacade downloader = null;
        AudioJsonParser audioJsonParser = new AudioJsonParser(downloader);
        Track track = audioJsonParser.parseJsonStringToTrack(new StringReader(testJson));
        assertEquals("trackReference", track.reference);
        assertEquals(new URL("http://url.com"), track.mediaFile.fileName);
    }
}
