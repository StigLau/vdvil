package no.lau.kpro.model;

import no.lau.kpro.domain.Topic;
import no.lau.kpro.domain.TopicIdentifyable;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jun 22, 2008
 */
public class YellowCircle implements Circle, TopicIdentifyable {
    Topic topic;
    String tastesLike;

    public static final YellowCircle NULL = new YellowCircle(Topic.NULL);

    public YellowCircle(Topic topic) {
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }

    public String getTastesLike() {
        return tastesLike;
    }

    public void setTastesLike(String tastesLike) {
        this.tastesLike = tastesLike;
    }
}
