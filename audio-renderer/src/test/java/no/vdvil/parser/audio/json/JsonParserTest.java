package no.vdvil.parser.audio.json;

import com.google.gson.Gson;
import no.lau.vdvil.cache.DownloaderFacade;
import no.lau.vdvil.cache.SimpleCacheImpl;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.handler.persistence.DvlXML;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.timing.Interval;
import no.vdvil.renderer.audio.MediaFile;
import no.vdvil.renderer.audio.Segment;
import no.vdvil.renderer.audio.TestMp3s;
import no.vdvil.renderer.audio.Track;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class JsonParserTest {

    String testJson = "{\"reference\":\"trackReference\",\"bpm\":123.0,\"mediaFile\":{\"fileName\":\"http://url.com\",\"startingOffset\":1000.0,\"checksum\":\"a checksum\"},\"segments\":[{\"id\":\"id1\",\"text\":\"hello\",\"start\":0,\"end\":16},{\"id\":\"id2\",\"text\":\"goodbye\",\"start\":16,\"end\":32}]}";
    Logger logger = LoggerFactory.getLogger(this.getClass());

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
        JsonAudioParser audioJsonParser = new JsonAudioParser(downloader);
        Track track = audioJsonParser.parseJsonStringToTrack(new StringReader(testJson));
        assertEquals("trackReference", track.reference);
        assertEquals(new URL("http://url.com"), track.mediaFile.fileName);
    }
    @Test
    public void jsonReturning() throws MalformedURLException {
        System.out.println(new Gson().toJson(TestMp3s.returning));
    }

    @Test
    public void testParsingReturning() throws IOException {
        InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("/Returning.dvl.json"));
        Track track = new JsonAudioParser(null).parseJsonStringToTrack(reader);
        printTrackProperties(track);
        assertEquals(new Float(130.0F), track.bpm);
        assertEquals(15, track.segments.size());

        checkSegment(track.segments.get(0), "4336519975847252321", "low", 0, 64);
        checkSegment(track.segments.get(1), "4638184666682848978", "", 64, 128);
        checkSegment(track.segments.get(2), "2754708889643705332", "Up", 128, 256);
        checkSegment(track.segments.get(3), "4533227407229953527", "Down", 256, 320);
        checkSegment(track.segments.get(4), "6401936245564505757", "Setting up", 320, 416);
        checkSegment(track.segments.get(5), "30189981949854134", "Want nothing 1. time", 416, 448);
        checkSegment(track.segments.get(6), "6182122145512625145", "", 448, 480);
        checkSegment(track.segments.get(7), "6978423701190173373", "Action satisfaction", 480, 512);
        checkSegment(track.segments.get(8), "3657904262668647219", "Calming synth", 512, 544);
        checkSegment(track.segments.get(9), "3378726703924324403", "Lyrics - 1. part", 544, 576);
        checkSegment(track.segments.get(10), "4823965795648964701", "want nothing - 2. part", 576, 608);
        checkSegment(track.segments.get(11), "5560598317419002938", "want nothing - 2. time", 608, 640);
        checkSegment(track.segments.get(12), "9040781467677187716", "want nothing - 3. time", 640, 704);
        checkSegment(track.segments.get(13), "5762690949488488062", "synth", 704, 768);
        checkSegment(track.segments.get(14), "651352148519104110", "Want nothing - 3. time", 768, 1024);
    }

    void checkSegment(Segment segment, String id, String text, int start, int length) {
        assertEquals(id, segment.id);
        assertEquals(text, segment.text);
        assertEquals(start, segment.start);
        assertEquals(length, segment.end);
    }

    void printTrackProperties(Track track) {
        System.out.println();
        for (int i = 0; i < track.segments.size(); i++) {
            Segment seg = track.segments.get(i);
            System.out.println("checkSegment(track.segments.get(" + i + "), \"" + seg.id + "\", \"" + seg.text + "\", " + seg.start + ", " + seg.end + ");");
        }
    }
}
