package no.lau.kpro;

import no.lau.kpro.domain.Topic;
import no.lau.kpro.domain.Context;
import no.lau.kpro.domain.TimeInterval;

import java.util.List;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jul 21, 2008
 */
public interface Store {
    <T> List<T> findTopicsByClass(Class<T> theClass);

    <T> List<T> findTopicsExtendingClass(Class<T> classToFind);

    <V> V findObjectByTopicAndClass(Topic topic, Class aClass);

    Topic findTopicById(String id);

    List<Topic> findTopicsByContext(Context context);

    List<Topic> findTopicsByTimeInterval(TimeInterval timeInterval);

    List<Topic> findTopicsByContextAndTime(Context context, TimeInterval timeInterval);
}
