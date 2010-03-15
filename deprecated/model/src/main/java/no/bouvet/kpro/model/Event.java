package no.bouvet.kpro.model;

import java.util.List;

/**
 * @author Stig Lau
 */
public interface Event {
    Event getParent();

    List<Event> getChildren();

    Media getMedia();

    List<Lyric> getLyrics();

    Double getStartTime();

    Double getLength();

    Double getBPM();

    String getDescription();

    String getName();
}
