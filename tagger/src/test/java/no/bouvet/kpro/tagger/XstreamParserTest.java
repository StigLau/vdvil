package no.bouvet.kpro.tagger;

import org.testng.annotations.Test;
import org.testng.Assert;
import no.lau.tagger.model.SimpleSong;
import no.bouvet.kpro.tagger.persistence.XStreamParser;

public class XstreamParserTest {
    XStreamParser parser = new XStreamParser();

    @Test
    public void parseWithXStream() {
        SimpleSong joe = SimpleSongParserTest.coronaTest();

        //Serialize
        String xml = parser.toXml(joe);
        System.out.println(xml);
        //Deserialize
        SimpleSong newJoe = parser.fromXML(xml);
        Assert.assertEquals(joe.mediaFile.fileName, newJoe.mediaFile.fileName);
    }

    @Test
    public void writeWithXstreamParser() {
        String xml = parser.toXml(SimpleSongParserTest.coronaTest());
        
        SimpleSong simpleSong = parser.fromXML(xml);
        Assert.assertEquals(simpleSong.mediaFile.fileName, "/Users/stiglau/jobb/utvikling/bouvet/playground/stig.lau/kpro2007/renderer.audio/src/test/resources/Corona_-_Baby_Baby.mp3");
    }
}
