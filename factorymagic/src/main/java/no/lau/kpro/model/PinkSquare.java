package no.lau.kpro.model;

import no.lau.kpro.domain.TopicIdentifyable;
import no.lau.kpro.domain.Topic;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jul 13, 2008
 */
public class PinkSquare extends Square implements TopicIdentifyable {
    private Topic topic;

    public PinkSquare(Topic topic) {
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }
}
