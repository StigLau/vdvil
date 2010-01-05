package no.bouvet.kpro.persistence;

import org.junit.Test;
import org.junit.Assert;
import no.bouvet.kpro.model.stigstest.TopicMapEvent;
import no.bouvet.kpro.model.Event;

public class EventTest {

    @Test
    public void testReadingStartField() {
        Event testEvent = new TopicMapEvent("https://wiki.bouvet.no/snap-starting-out");
        Assert.assertEquals(0D, testEvent.getStartTime().doubleValue());
        Assert.assertEquals(120D, testEvent.getBPM().doubleValue());
        Assert.assertEquals(240D, testEvent.getLength().doubleValue());
    }

    @Test
    public void testGetLyrics() {
        Event event = new TopicMapEvent("https://wiki.bouvet.no/snap-starting-out");
        Assert.assertEquals(1, event.getLyrics().size());
        Assert.assertEquals("Snap Starting Out", event.getLyrics().get(0).getText());
    }
}
