package no.bouvet.kpro.model;

import java.util.List;

/**
 * @author Stig Lau
 */
public interface Media {
    List<Event> getEvents();

    String getName();
}
