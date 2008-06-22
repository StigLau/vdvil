package no.lau.kpro.domain;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jun 22, 2008
 */
public class Context {

    String id;

    public Context(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
