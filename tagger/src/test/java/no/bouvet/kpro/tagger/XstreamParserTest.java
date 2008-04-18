package no.bouvet.kpro.tagger;

import org.testng.annotations.Test;
import org.testng.Assert;
import no.bouvet.kpro.tagger.model.SimpleSong;
import no.bouvet.kpro.tagger.model.Row;
import no.bouvet.kpro.tagger.persistence.XStreamParser;
import com.thoughtworks.xstream.XStream;

public class XstreamParserTest {

    @Test
    public void parseWithXStream() {
        XStream xstream = new XStream();

        xstream.alias("song", SimpleSong.class);
        xstream.alias("row", Row.class);

        SimpleSong joe = SimpleSongParserTest.coronaTest();

        //Serialize
        String xml = xstream.toXML(joe);
        System.out.println(xml);
        //Deserialize
        SimpleSong newJoe = (SimpleSong)xstream.fromXML(xml);
    }

    @Test
    public void writeWithXstreamParser() {
        XStreamParser parser = new XStreamParser<SimpleSong>();
        parser.save(SimpleSongParserTest.coronaTest(), parser.path + "/corona.dvl");
        
        SimpleSong simpleSong = (SimpleSong) parser.load(parser.path + "/corona.dvl");
        Assert.assertEquals(simpleSong.fileName, "/Volumes/McFeasty/Users/Stig/jobb/utvikling/bouvet/playground/stig.lau/kpro2007/renderer.audio/src/test/resources/Corona_-_Baby_Baby.mp3");
    }
}
