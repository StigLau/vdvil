package no.bouvet.kpro.persistence;

import org.junit.Test;import static org.junit.Assert.assertEquals;
import no.bouvet.kpro.model.stigstest.Media;
import no.bouvet.kpro.model.stigstest.Event;

import java.util.List;

public class EventTest {

    @Test
    public void testReadingStartField() {
        Media rythmMp3 = new Media("https://wiki.bouvet.no/snap-rythm_is_a_dancer");
        List<Event> topEvent = rythmMp3.getEvents();
        assertEquals(0D, topEvent.get(0).getStartTime());
        assertEquals(31902.382D, topEvent.get(0).getLength());
    }
}
