package no.bouvet.kpro;

import no.bouvet.kpro.model.Event;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Stig Lau
 */
public class TopicMapUtil {

    public static void sortEventsByTime(List<Event> events) {
        Collections.sort(events, new Comparator<Event>() {
            public int compare(Event a, Event b) {
                return (int) (a.getStartTime() - b.getStartTime());
            }
        });
    }
}
