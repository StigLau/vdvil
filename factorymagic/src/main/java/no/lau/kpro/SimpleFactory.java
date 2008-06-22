package no.lau.kpro;

import no.lau.kpro.domain.Topic;
import no.lau.kpro.domain.Context;
import no.lau.kpro.domain.TimeInterval;

import java.util.*;

import org.apache.commons.lang.Validate;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jun 20, 2008
 */
public class SimpleFactory {

    //List of all topics
    Map<String, Topic> topics = new HashMap<String, Topic>();

    //Topic - Context mappings
    Map<Context, List<Topic>> contextTopicMap = new HashMap<Context, List<Topic>>();

    //Topic - TimeInterval mappings
    Map<TimeInterval, List<Topic>> timeTopicMap = new HashMap<TimeInterval, List<Topic>>();

    public Object getTopicById(String id) {
        return topics.get(id);
    }

    public void storeTopic(Topic topic) {
        Validate.notEmpty(topic.getId());
        topics.put(topic.getId(), topic);
    }

    public void putTopicIntoContext(Topic topic, Context context) {
        List<Topic> topicList = contextTopicMap.get(context);
        if (topicList == null) {
            topicList = new ArrayList<Topic>();
            contextTopicMap.put(context, topicList);
        }
        topicList.add(topic);
    }

    public void putTopicIntoTime(Topic topic, TimeInterval timeInterval) {
        List<Topic> topicList = timeTopicMap.get(timeInterval);
        if (topicList == null) {
            topicList = new ArrayList<Topic>();
            timeTopicMap.put(timeInterval, topicList);
        }
        topicList.add(topic);
    }

    public List<Topic> findTopicsByContext(Context context) {
        List<Topic> result = contextTopicMap.get(context);
        if (result == null)
            return new ArrayList<Topic>();
        else
            return result;
    }

    public List<Topic> findTopicsByTimeInterval(TimeInterval timeInterval) {
        List<Topic> result = new ArrayList<Topic>();

        for (TimeInterval interval : timeTopicMap.keySet()) {
            if(interval.containsInterval(timeInterval)) {
                result.addAll(timeTopicMap.get(interval));
            }
        }

        return result;
    }


    public List<Topic> findTopicsByContextAndTime(Context context, TimeInterval timeInterval) {
        List<Topic> timeTopics = findTopicsByTimeInterval(timeInterval);
        List<Topic> contextTopics = findTopicsByContext(context);
        return findObjectsWhichExistInBothLists(contextTopics, timeTopics);
    }

    private <T> List<T> findObjectsWhichExistInBothLists(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<T>();
        for (T object : list1) {
            if(list2.contains(object)) {
                result.add(object);
            }
        }
        return result;
    }

    public Topic findTopicById(String id) {
        return topics.get(id);
    }
}

