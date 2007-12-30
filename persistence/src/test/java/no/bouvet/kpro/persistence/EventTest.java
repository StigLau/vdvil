package no.bouvet.kpro.persistence;

import org.junit.Test;import static org.junit.Assert.assertEquals;
import no.bouvet.kpro.model.stigstest.Media;
import no.bouvet.kpro.model.stigstest.Event;

import java.util.List;

public class EventTest {

    @Test
    public void testReadingStartField() {
        Event testEvent = new Event("https://wiki.bouvet.no/snap-starting-out");
        assertEquals(0D, testEvent.getStartTime());
        assertEquals(120D, testEvent.getBPM());
        assertEquals(240D, testEvent.getLength());
    }

    @Test
    public void testGetLyrics() {
        Event event = new Event("https://wiki.bouvet.no/snap-starting-out");
        assertEquals(1, event.getLyrics().size());
        assertEquals("Snap Starting Out", event.getLyrics().get(0).getText());
    }
}
