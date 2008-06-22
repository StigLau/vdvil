package no.lau.kpro;

import no.lau.kpro.domain.Topic;
import no.lau.kpro.domain.Context;
import no.lau.kpro.domain.TimeInterval;
import no.lau.kpro.model.Yellow;

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

    //TopicsByClass
    Map<Class, List<Object>> classTopicMap = new HashMap<Class, List<Object>>();

    //object - Topic mappongs
    Map<Class, List<Object>> objectClassMap = new HashMap<Class, List<Object>>();


    //Setters

    public void storeTopic(Topic topic) {
        Validate.notEmpty(topic.getId());
        topics.put(topic.getId(), topic);
    }

    public void putTopicIntoContext(Topic topic, Context context) {
        insertIntoMap(context, topic, contextTopicMap);
    }

    public void putTopicIntoTime(Topic topic, TimeInterval timeInterval) {
        insertIntoMap(timeInterval, topic, timeTopicMap);
    }

    public void putTopicIntoClass(Topic topic, Class aClass) {
        insertIntoMap(aClass, topic, classTopicMap);
    }


    public Yellow findTopicByIdAndClass(Topic topic2, Class<Yellow> yellowClass) {
        List<Object> yellowList = findInMap(yellowClass, objectClassMap);
        for (Object o : yellowList) {
            if (o instanceof Yellow) {
                Yellow yellow = (Yellow) o;
                if (yellow.getTopic() == topic2) {
                    return yellow;
                }
            }
        }
        return Yellow.NULL;
    }

    public <T> void putObjectIntoTopicMap(T object) {
        insertIntoMap(object.getClass() , object, objectClassMap);
    }

    //Finders

    public Topic findTopicById(String id) {
        return topics.get(id);
    }

    public List<Topic> findTopicsByContext(Context context) {
        return findInMap(context, contextTopicMap);
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

    public <T> List<T> findTopicsByClass(Class<T> theClass) {
        return (List<T>) classTopicMap.get(theClass);
    }


    // Helpers

    private <K, V> void insertIntoMap(K key, V value, Map<K, List<V>> map) {
        List<V> valueList = map.get(key);
        if (valueList == null) {
            valueList = new ArrayList<V>();
            map.put(key, valueList);
        }
        valueList.add(value);
    }

    private <K, V> List<V> findInMap(K key, Map<K, List<V>> map) {
        List<V> result = map.get(key);
        if (result == null)
            return new ArrayList<V>();
        else
            return result;
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
}

