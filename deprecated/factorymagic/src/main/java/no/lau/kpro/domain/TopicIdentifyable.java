package no.lau.kpro.domain;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jun 22, 2008
 */
public interface TopicIdentifyable {
    TopicIdentifyable NULL = new TopicIdentifyable() {
        public Topic getTopic() {
            return Topic.NULL;
        }
    };

    Topic getTopic();

}
