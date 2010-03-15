package no.bouvet.kpro.persistence;

import no.bouvet.kpro.model.stigstest.TopicMapEvent;
import no.bouvet.kpro.model.stigstest.TopicMapMedia;
import no.bouvet.kpro.model.Event;
import no.bouvet.kpro.model.Media;
import no.bouvet.topicmap.query.ITologQuery;
import no.bouvet.topicmap.query.SimpleTologQueryString;
import no.bouvet.topicmap.query.SimpleTopicParameterFactory;
import no.bouvet.topicmap.core.TopicMap;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import org.apache.log4j.Logger;
import java.util.List;

import net.ontopia.topicmaps.core.TopicIF;


public class TestMediaEvent {

    public static TopicMapMedia rythmMp3;
    static Logger log = Logger.getLogger(TestMediaEvent.class);

    @BeforeClass
    public static void setUp() {
        rythmMp3 = new TopicMapMedia("https://wiki.bouvet.no/snap-rythm_is_a_dancer");
    }

    @Test
    public void testGetMediaName() {
        assertEquals("Snap - Rhythm is a Dancer.mp3", rythmMp3.getName());
    }

    @Test
    public void testGetAllEventsOfSnap() {
        List<Event> events = rythmMp3.getEvents();
        assertEquals(5, events.size());
    }
    
    @Test
    public void testGetParentEventName() {
        Event rythm = new TopicMapEvent("oid:snap-rythm");
        assertEquals("Snap - Rythm is a Dancer", rythm.getName());
    }

    @Test
    public void testGetMediaFromEventName() {
        Event rythm = new TopicMapEvent("oid:snap-rythm");
        Media media = rythm.getMedia();
        assertEquals("Snap - Rhythm is a Dancer.mp3", media.getName());
    }

    @Test
    public void testGetChildren() {
        Event rythm = new TopicMapEvent("oid:snap-rythm");
        List<Event> children = rythm.getChildren();
        for (Event child : children) {
            log.debug(child.getName());
        }
        assertEquals(5, children.size());
    }

    @Test
    public void testGetParent() {
        Event rythm = new TopicMapEvent("oid:snap-rythm");
        Event child = rythm.getChildren().get(0);
        Event parent = child.getParent();
        assertEquals("Snap - Rythm is a Dancer", parent.getName());
    }

    @Test
    public void testMediaEventAssociation2() {
        Event rythm = new TopicMapEvent("oid:snap-rythm");
        TopicMapEvent child = (TopicMapEvent) rythm.getChildren().get(0);
        TopicMap tm = new VaudevilleTopicMap("musikk.xtm");
        ITologQuery tologQuery = new SimpleTologQueryString("vdvil:part-whole($WHOLE : vdvil:whole, %PART% : vdvil:part)?", child, "PART");
        List<TopicIF> resutl = tm.queryForList(tologQuery, SimpleTopicParameterFactory.create("WHOLE"));
        assertEquals(1, resutl.size());
        Event event = new TopicMapEvent(resutl.get(0));
        assertEquals("Snap - Rythm is a Dancer", event.getName());
    }

}