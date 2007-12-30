package no.bouvet.kpro.model.stigstest;

import no.bouvet.topicmap.query.*;
import no.bouvet.topicmap.dao.TopicDAO;
import no.bouvet.topicmap.dao.TopicType;
import no.bouvet.kpro.persistence.VaudevilleAssociationType;
import no.bouvet.kpro.persistence.VaudevilleTopicType;
import no.bouvet.kpro.persistence.VaudevilleTopicMap;
import no.bouvet.kpro.model.Event;
import no.bouvet.kpro.model.Lyric;
import no.bouvet.kpro.model.Media;
import java.util.List;
import java.util.ArrayList;
import net.ontopia.topicmaps.core.TopicIF;
import org.apache.log4j.Logger;

public class TopicMapEvent extends TopicDAO implements no.bouvet.kpro.model.Event {
    static Logger log = Logger.getLogger(TopicMapEvent.class);

    private static VaudevilleTopicMap tm;

    static {
        tm = new VaudevilleTopicMap("musikk.xtm");
    }

    public TopicMapEvent(String psi) {
        super(tm.getTopicDAOByPSI(psi).getTopicIF());
    }

    public TopicMapEvent(TopicIF topicIF) {
        super(topicIF);
    }

    static {
        tm = new VaudevilleTopicMap("musikk.xtm");
    }


    public Event getParent() {
        TologQuery tologQuery = new TologQuery(VaudevilleAssociationType.PART_WHOLE, new StandardTopicParameter(this), new StandardTopicParameter(VaudevilleTopicType.WHOLE));
        TopicIF topic = tm.queryForSingleValue(tologQuery, new StandardTopicParameter(VaudevilleTopicType.WHOLE));
        return new TopicMapEvent(topic);
    }

    public List<Event> getChildren() {
        List<Event> results = new ArrayList<Event>();
        TologQuery tologQuery = new TologQuery(VaudevilleAssociationType.PART_WHOLE, new StandardTopicParameter(VaudevilleTopicType.PART), new StandardTopicParameter(this));
        List<TopicIF> topics = tm.queryForList(tologQuery, new StandardTopicParameter(VaudevilleTopicType.PART));
        for (TopicIF topic : topics) {
            results.add(new TopicMapEvent(topic));
        }
        return results;
    }

    public Media getMedia() {
        TologQuery tologQuery = new TologQuery(VaudevilleAssociationType.MEDIA_EVENT, new StandardTopicParameter(VaudevilleTopicType.MEDIA), new StandardTopicParameter(this));
        TopicIF topicIF = tm.queryForSingleValue(tologQuery, new StandardTopicParameter(VaudevilleTopicType.MEDIA));
        if(topicIF != null)
            return new TopicMapMedia(topicIF);
        else
            return getParent().getMedia();
    }

    public List<Lyric> getLyrics() {
        TologQuery tologQuery = new TologQuery(VaudevilleAssociationType.EVENT_LYRIC, new StandardTopicParameter(VaudevilleTopicType.LYRIC), new StandardTopicParameter(this));
        List<TopicIF> topicIFList = tm.queryForList(tologQuery, new StandardTopicParameter(VaudevilleTopicType.LYRIC));

        List<Lyric> lyrics = new ArrayList<Lyric>();
        for (TopicIF topicIF : topicIFList) {
            lyrics.add(new TopicMapLyric(topicIF));
        }
        return lyrics;
    }

    public Double getStartTime() {
        ITologQuery tologQuery = new SimpleTologQueryString("vdvil:start(%TOPIC%, $START)?", this, "TOPIC");
        String value = tm.queryForSingleValue(tologQuery, SimpleTopicParameterFactory.create("START"));
        return Double.valueOf(value);
    }

    public Double getLength() {
        ITologQuery tologQuery = new SimpleTologQueryString("vdvil:length(%TOPIC%, $LENGTH)?", this, "TOPIC");
        String value = tm.queryForSingleValue(tologQuery, SimpleTopicParameterFactory.create("LENGTH"));
        return Double.valueOf(value);
    }
    
    public Double getBPM() {
        ITologQuery tologQuery = new SimpleTologQueryString("vdvil:bpm(%TOPIC%, $BPM)?", this, "TOPIC");
        String value = tm.queryForSingleValue(tologQuery, SimpleTopicParameterFactory.create("BPM"));
        return Double.valueOf(value);
    }

    public String getDescription() {
        ITologQuery tologQuery = new SimpleTologQueryString("purl:description(%TOPIC%, $DESCRIPTION)?", this, "TOPIC");
        return tm.queryForSingleValue(tologQuery, SimpleTopicParameterFactory.create("DESCRIPTION"));
    }

    public Float getRate() {
        log.info("Not implemented yet - Event.getRate()");
        return 1F;
    }

    public Float getVolume() {
        log.info("Not implemented yet - Event.getVolume()");
        return 1F;
    }

    public TopicType getTopicType() {
        return VaudevilleTopicType.EVENT;
    }

    public String toString() {
        return
                "Name:" + this.getName() +
                       "StartTime:" + this.getStartTime() +
                        "Length:" + this.getLength() + 
                        " - " + super.toString();
    }
}
