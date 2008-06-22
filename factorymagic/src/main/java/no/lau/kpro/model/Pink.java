package no.lau.kpro.model;

import no.lau.kpro.domain.TopicClass;
import no.lau.kpro.domain.Topic;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jun 22, 2008
 */
public class Pink implements TopicClass {
    private Topic topic;

    public Pink(Topic topic) {
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }
}
