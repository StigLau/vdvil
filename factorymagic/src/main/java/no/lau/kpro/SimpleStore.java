package no.lau.kpro;

import no.lau.kpro.domain.Topic;
import no.lau.kpro.domain.Context;
import no.lau.kpro.domain.TimeInterval;
import no.lau.kpro.domain.TopicIdentifyable;

import java.util.*;

import org.apache.commons.lang.Validate;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jun 20, 2008
 */
public class SimpleStore {

    //List of all topics
    Map<String, Topic> topics = new HashMap<String, Topic>();

    //Topic - Context mappings
    Map<Context, List<Topic>> contextTopicMap = new HashMap<Context, List<Topic>>();

    //Topic - TimeInterval mappings
    Map<TimeInterval, List<Topic>> timeTopicMap = new HashMap<TimeInterval, List<Topic>>();

    //object - Topic mappings
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

    public <T> List<T> findTopicsByClass(Class<T> theClass) {
        List<T> result = (List<T>) objectClassMap.get(theClass);
        return result == null ? new ArrayList<T>() : result; 
    }

    /**
     * Find all topics of this class and its inherited classes
     * @param classToFind
     * @return
     */
    public <T> List<T> findTopicsExtendingClass(Class<T> classToFind) {
        Set<Class> keySet = objectClassMap.keySet();

        Set<Class> keySetToUse = new HashSet<Class>();

        for (Class currentClass : keySet) {
            if(classToFind.isAssignableFrom(currentClass)) {
                keySetToUse.add(currentClass);
            }
        }

        List<T> result = new ArrayList<T>();
        for (Class currentClass : keySetToUse) {
            result.addAll((List<T>)objectClassMap.get(currentClass));
        }
        return result;
    }

    public <V> V findObjectByTopicAndClass(Topic topic, Class aClass) {
        List<Object> objectList = findInMap(aClass, objectClassMap);
        for (Object o : objectList) {
            TopicIdentifyable object = (TopicIdentifyable) o;
            if (object.getTopic() == topic) {
                return (V) object;
            }
        }
        return (V) TopicIdentifyable.NULL;
    }

    public <T extends TopicIdentifyable> void putObjectIntoTopicMap(T object) {
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

