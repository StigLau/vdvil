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
public class TestBindingApp {

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

        simpleFactory.putTopicIntoClass(topic1, Pink.class);
        simpleFactory.putTopicIntoClass(topic1, Yellow.class);

        topic2 = new Topic("2");
        simpleFactory.storeTopic(topic2);
        simpleFactory.putTopicIntoContext(topic2, lyricContext);
        simpleFactory.putTopicIntoTime(topic2, new TimeInterval(100, 199));
        simpleFactory.putTopicIntoClass(topic2, Pink.class);
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
        List<Pink> pinks = simpleFactory.findTopicsByClass(Pink.class);

    }
}
