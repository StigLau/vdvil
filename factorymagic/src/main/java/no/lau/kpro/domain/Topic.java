package no.lau.kpro.domain;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jun 22, 2008
 */
public class Topic {
    String id;
    public static final Topic NULL = new Topic("NULL");

    public Topic(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return "Topic id:" + id;
    }
}
