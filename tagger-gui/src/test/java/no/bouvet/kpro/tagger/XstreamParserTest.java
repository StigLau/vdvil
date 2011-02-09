package no.bouvet.kpro.tagger;

import no.lau.tagger.model.MediaFile;
import no.lau.tagger.model.Segment;
import no.lau.tagger.model.SimpleSong;
import no.bouvet.kpro.tagger.persistence.XStreamParser;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
//TODO Check out this outdated test!!!
public class XstreamParserTest {
    XStreamParser parser = new XStreamParser();
    static Logger log = Logger.getLogger(XstreamParserTest.class);

    @Test
    public void parseWithXStream() {
        SimpleSong joe = coronaTest();

        //Serialize
        String xml = parser.toXml(joe);
        log.debug(xml);
        //Deserialize
        SimpleSong newJoe = parser.fromXML(xml);
        Assert.assertEquals(joe.mediaFile.fileName, newJoe.mediaFile.fileName);
    }

    @Test
    public void writeWithXstreamParser() {
        String xml = parser.toXml(coronaTest());
        
        SimpleSong simpleSong = parser.fromXML(xml);
        Assert.assertEquals(simpleSong.mediaFile.fileName, "/Users/stiglau/jobb/utvikling/bouvet/playground/stig.lau/kpro2007/renderer.audio/src/test/resources/Corona_-_Baby_Baby.mp3");
    }

    public static SimpleSong coronaTest() {
        MediaFile mediaFile = new MediaFile("/Users/stiglau/jobb/utvikling/bouvet/playground/stig.lau/kpro2007/renderer.audio/src/test/resources/Corona_-_Baby_Baby.mp3", "",
                44100 * 0.445f);
        List<Segment> segments = new ArrayList<Segment>();
        segments.add(new Segment("a", 0, 16,  "Baby, why can't we just stay together"));
        segments.add(new Segment("b", 16, 32,  "Baby, why can't we just stay together"));
        segments.add(new Segment("c", 32, 64,  "Intro"));
        segments.add(new Segment("d", 64, 96,  "Riff 1. time"));
        segments.add(new Segment("e", 96, 128,  "1. Refrain  I want to roll inside your soul,"));
        segments.add(new Segment("f", 128, 128 + 32,  "2. Verse - Caught you down by suprise"));
        segments.add(new Segment("g", 128 + 32, 128 + 64,  "Baby baby, why can't we just stay together"));
        segments.add(new Segment("h", 128 + 64, 128 + 96, "riff 2. time"));
        segments.add(new Segment("i", 128 + 96, 256,  "Deep inside I know you need it"));
        segments.add(new Segment("j", 256, 256 + 32, "Caught you down by suprise"));
        return new SimpleSong("Corona - Baby baby", mediaFile, segments, 132.98f);
    }
}
