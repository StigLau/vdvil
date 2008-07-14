package no.lau.kpro;

import org.testng.annotations.*;
import static org.junit.Assert.*;
import no.lau.kpro.domain.Topic;
import no.lau.kpro.domain.Context;
import no.lau.kpro.domain.TimeInterval;
import no.lau.kpro.model.Pink;
import no.lau.kpro.model.YellowCircle;
import no.lau.kpro.model.PinkSquare;
import no.lau.kpro.model.Square;

import java.util.List;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jun 22, 2008
 * Test of the vital elements of the FactoryMagic
 */
public class SimpleStoreTest {

    SimpleStore simpleStore = new SimpleStore();

    Context lyricContext = new Context("lyrics");
    Topic topic1;
    Topic topic2;

    @BeforeTest
    public void setUpData() {
        topic1 = new Topic("1");
        simpleStore.storeTopic(topic1);
        simpleStore.putTopicIntoContext(topic1, lyricContext);
        simpleStore.putTopicIntoTime(topic1, new TimeInterval(0, 99));
        simpleStore.putObjectIntoTopicMap(new Pink(topic1));

        topic2 = new Topic("2");
        simpleStore.storeTopic(topic2);
        simpleStore.putTopicIntoContext(topic2, lyricContext);
        simpleStore.putTopicIntoTime(topic2, new TimeInterval(100, 199));
        simpleStore.putObjectIntoTopicMap(new Pink(topic2));

        YellowCircle topic2Yellow = new YellowCircle(topic2);
        topic2Yellow.setTastesLike("curry");
        simpleStore.putObjectIntoTopicMap(topic2Yellow);
    }

    @Test
    public void testFindTopicById() {
        assertEquals(topic1, simpleStore.findTopicById("1"));
        assertEquals(topic2, simpleStore.findTopicById("2"));
    }

    @Test
    public void testFindTopicsByContext() {
        List<Topic> result = simpleStore.findTopicsByContext(lyricContext);
        assertEquals(2, result.size());
    }

    @Test
    public void testFindTopicsByContextAndTime() {
        TimeInterval timeInterval = new TimeInterval(20, 25);

        List<Topic> result = simpleStore.findTopicsByContextAndTime(lyricContext, timeInterval);
        assertEquals(1, result.size());
        assertEquals(topic1, result.get(0));
    }

    @Test
    public void testFindTopicsByClass() {
        assertEquals(2, simpleStore.findTopicsByClass(Pink.class).size());
        assertEquals(1, simpleStore.findTopicsByClass(YellowCircle.class).size());
    }

    @Test
    public void testCreatingObjectByTopicId() {
        YellowCircle yellowSomething = simpleStore.findObjectByTopicAndClass(topic2, YellowCircle.class);
        assertEquals(topic2, yellowSomething.getTopic());
        assertEquals(yellowSomething.getTastesLike(), "curry");
    }


    @Test
    public void testInstanceWorks() throws IllegalAccessException, InstantiationException {
        Class ps = Square.class;
        assert ps.isInstance(new PinkSquare(topic1));
        assert ps.isAssignableFrom(PinkSquare.class);
    }

    @Test
    public void testFindingInstanceOfExtendingClasses() {
        simpleStore.putObjectIntoTopicMap(new PinkSquare(new Topic("pinkSquare")));

        List<Square> mySquares = simpleStore.findTopicsExtendingClass(Square.class);
        assertEquals(mySquares.size(), 1);

        simpleStore.putObjectIntoTopicMap(new PinkSquare(new Topic("pinkSquare2")));
        assertEquals(simpleStore.findTopicsExtendingClass(Square.class).size(), 2);
    }
}
