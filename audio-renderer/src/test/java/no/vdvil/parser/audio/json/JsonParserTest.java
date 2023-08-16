package no.vdvil.parser.audio.json;

import com.google.gson.Gson;
import no.vdvil.renderer.audio.MediaFile;
import no.vdvil.renderer.audio.Segment;
import no.vdvil.renderer.audio.TestMp3s;
import no.vdvil.renderer.audio.Track;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonParserTest {

    final String testJson = "{\"reference\":\"trackReference\",\"bpm\":123.0,\"mediaFile\":{\"fileName\":\"https://url.com\",\"startingOffset\":1000.0,\"checksum\":\"a checksum\"},\"segments\":[{\"id\":\"id1\",\"text\":\"hello\",\"start\":0,\"duration\":16,\"end\":16},{\"id\":\"id2\",\"text\":\"goodbye\",\"start\":16,\"duration\":16,\"end\":32}]}";

    @Test
    public void testWritingTrackToJson() throws URISyntaxException {
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment("id1", 0, "hello", 16));
        segments.add(new Segment("id2", 16, "goodbye", 16));

        Track track = new Track("trackReference", 123F, new MediaFile(new URI("https://url.com"), 1000F, "a checksum"), segments);
        Gson gson = new Gson();
        String jsonResult = gson.toJson(track);
        System.out.println(jsonResult);
        assertEquals(testJson, jsonResult);
    }

    @Test
    public void testParsingJson() throws URISyntaxException {
        AudioJsonParser audioJsonParser = new AudioJsonParser();
        Track track = audioJsonParser.parseJsonStringToTrack(new StringReader(testJson));
        assertEquals("trackReference", track.reference);
        assertEquals(new URI("https://url.com"), track.mediaFile.fileName);
    }
    @Test
    public void jsonReturning() {
        System.out.println(new Gson().toJson(TestMp3s.returning));
    }

    @Test
    public void testParsingReturning() {
        InputStream stream = this.getClass().getResourceAsStream("/Returning.dvl.json");
        InputStreamReader reader = new InputStreamReader(stream);
        Track track = new AudioJsonParser().parseJsonStringToTrack(reader);
        printTrackProperties(track);
        assertEquals(130F, track.bpm);
        assertEquals(15, track.segments.size());

        checkSegment(track.segments.get(0), "4336519975847252321", "low", 0, 64);
        checkSegment(track.segments.get(1), "4638184666682848978", "", 64, 64);
        checkSegment(track.segments.get(2), "2754708889643705332", "Up", 128, 128);
        checkSegment(track.segments.get(3), "4533227407229953527", "Down", 256, 64);
        checkSegment(track.segments.get(4), "6401936245564505757", "Setting up", 320, 96);
        checkSegment(track.segments.get(5), "30189981949854134", "Want nothing 1. time", 416, 32);
        checkSegment(track.segments.get(6), "6182122145512625145", "", 448, 32);
        checkSegment(track.segments.get(7), "6978423701190173373", "Action satisfaction", 480, 32);
        checkSegment(track.segments.get(8), "3657904262668647219", "Calming synth", 512, 32);
        checkSegment(track.segments.get(9), "3378726703924324403", "Lyrics - 1. part", 544, 32);
        checkSegment(track.segments.get(10), "4823965795648964701", "want nothing - 2. part", 576, 32);
        checkSegment(track.segments.get(11), "5560598317419002938", "want nothing - 2. time", 608, 32);
        checkSegment(track.segments.get(12), "9040781467677187716", "want nothing - 3. time", 640, 64);
        checkSegment(track.segments.get(13), "5762690949488488062", "synth", 704, 64);
        checkSegment(track.segments.get(14), "651352148519104110", "Want nothing - 3. time", 768, 256);
    }

    void checkSegment(Segment segment, String id, String text, int start, int duration) {
        assertEquals(id, segment.id);
        assertEquals(text, segment.text);
        assertEquals(start, segment.start);
        assertEquals(duration, segment.duration);
    }

    void printTrackProperties(Track track) {
        System.out.println();
        for (int i = 0; i < track.segments.size(); i++) {
            Segment seg = track.segments.get(i);
            System.out.println("checkSegment(track.segments.get(" + i + "), \"" + seg.id + "\", \"" + seg.text + "\", " + seg.start + ", " + seg.duration + ");");
        }
    }
}
