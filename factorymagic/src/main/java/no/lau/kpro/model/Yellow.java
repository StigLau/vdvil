package no.lau.kpro.model;

import no.lau.kpro.domain.Topic;
import no.lau.kpro.domain.TopicClass;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jun 22, 2008
 */
public class Yellow implements TopicClass {
    Topic topic;
    String tastesLike;

    public static final Yellow NULL = new Yellow(Topic.NULL);

    public Yellow(Topic topic) {
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
