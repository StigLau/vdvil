package no.bouvet.kpro.persistence;

import no.bouvet.kpro.model.stigstest.Event;
import no.bouvet.kpro.model.stigstest.Media;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import java.util.List;


public class TestMediaEvent {

    public static Media rythmMp3;

    @BeforeClass
    public static void setUp() {
        rythmMp3 = new Media("https://wiki.bouvet.no/snap-rythm_is_a_dancer");
    }

    @Test
    public void testGetMediaName() {
        assertEquals("Snap - Rhythm is a Dancer.mp3", rythmMp3.getName());
    }

    @Test
    public void testGetParentEventName() {
        List<Event> events = rythmMp3.getEvents();
        assertEquals(1, events.size());
        Event babyBaby = events.get(0);
        assertEquals("Snap - Rythm is a Dancer", babyBaby.getName());
    }

    @Test
    public void testGetMediaFromEventName() {
        List<Event> events = rythmMp3.getEvents();
        Event singleEvent = events.get(0);
        Media media = singleEvent.getMedia();
        assertEquals("Snap - Rhythm is a Dancer.mp3", media.getName());
    }

    @Test
    public void testGetChildren() {
        Event babyBaby = rythmMp3.getEvents().get(0);
        List<Event> children = babyBaby.getChildren();
        for (Event child : children) {
            System.out.println(child.getName());
        }
        assertEquals(6, children.size());
    }

    @Test  // TODO: Problem - child.getparent returns 0 entries from the TopicMap
    public void testGetParent() {
        System.out.println("Parent");
        Event babyBaby = rythmMp3.getEvents().get(0);
        Event child = babyBaby.getChildren().get(0);
        System.out.println("Woot" + child.getName());
        Event parent = child.getParent();
        assertEquals("Baby Baby", parent.getName());
    }
}