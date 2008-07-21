package no.lau.kpro.domain;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jul 3, 2008
 */
public interface Receptor {
    public void put(TopicIdentifyable shape);

    public Class getAcceptsClass();
}
