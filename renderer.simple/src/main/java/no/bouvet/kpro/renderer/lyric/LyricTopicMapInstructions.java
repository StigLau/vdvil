package no.bouvet.kpro.renderer.lyric;

import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.model.Event;
import no.bouvet.kpro.TopicMapUtil;
import java.util.List;

/**
 * @author Stig Lau
 */
public class LyricTopicMapInstructions extends Instructions {

    public LyricTopicMapInstructions(Event event) {
        List<Event> parts = event.getChildren();

        TopicMapUtil.sortEventsByTime(parts);

        for ( Event part : parts ) {
            if(part.getLyrics().size() > 0) {
                append(new LyricInstruction(part));
            }
        }
    }

}
