package no.lau.kpro;

import org.testng.annotations.*;
import static org.junit.Assert.*;
import no.lau.kpro.domain.Topic;
import no.lau.kpro.domain.Context;
import no.lau.kpro.domain.TimeInterval;
import no.lau.kpro.model.Pink;
import no.lau.kpro.model.Yellow;

import java.util.List;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jun 22, 2008
 * Test of the vital elements of the FactoryMagic
 */
public class TestSimpleFactory {

    SimpleFactory simpleFactory = new SimpleFactory();

    Context lyricContext = new Context("lyrics");
    Topic topic1;
    Topic topic2;

    @BeforeTest
    public void setUpData() {
        topic1 = new Topic("1");
        simpleFactory.storeTopic(topic1);
        simpleFactory.putTopicIntoContext(topic1, lyricContext);
        simpleFactory.putTopicIntoTime(topic1, new TimeInterval(0, 99));
        simpleFactory.putObjectIntoTopicMap(new Pink(topic1));

        topic2 = new Topic("2");
        simpleFactory.storeTopic(topic2);
        simpleFactory.putTopicIntoContext(topic2, lyricContext);
        simpleFactory.putTopicIntoTime(topic2, new TimeInterval(100, 199));
        simpleFactory.putObjectIntoTopicMap(new Pink(topic2));

        Yellow topic2Yellow = new Yellow(topic2);
        topic2Yellow.setTastesLike("curry");
        simpleFactory.putObjectIntoTopicMap(topic2Yellow);
    }

    @Test
    public void testFindTopicById() {
        assertEquals(topic1, simpleFactory.findTopicById("1"));
        assertEquals(topic2, simpleFactory.findTopicById("2"));
    }

    @Test
    public void testFindTopicsByContext() {
        List<Topic> result = simpleFactory.findTopicsByContext(lyricContext);
        assertEquals(2, result.size());
    }

    @Test
    public void testFindTopicsByContextAndTime() {
        TimeInterval timeInterval = new TimeInterval(20, 25);

        List<Topic> result = simpleFactory.findTopicsByContextAndTime(lyricContext, timeInterval);
        assertEquals(1, result.size());
        assertEquals(topic1, result.get(0));
    }

    @Test
    public void testFindTopicsByClass() {
        assertEquals(2, simpleFactory.findTopicsByClass(Pink.class).size());
        assertEquals(1, simpleFactory.findTopicsByClass(Yellow.class).size());
    }

    @Test
    public void testCreatingObjectByTopicId() {
        Yellow yellowSomething = simpleFactory.findObjectByTopicAndClass(topic2, Yellow.class);
        assertEquals(topic2, yellowSomething.getTopic());
        assertEquals(yellowSomething.getTastesLike(), "curry");

    }
}
