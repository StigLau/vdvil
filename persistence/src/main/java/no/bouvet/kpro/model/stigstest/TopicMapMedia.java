package no.bouvet.kpro.model.stigstest;

import no.bouvet.topicmap.dao.TopicDAO;
import no.bouvet.topicmap.dao.TopicType;
import no.bouvet.topicmap.query.*;
import no.bouvet.kpro.persistence.VaudevilleTopicMap;
import no.bouvet.kpro.persistence.VaudevilleAssociationType;
import no.bouvet.kpro.persistence.VaudevilleTopicType;
import no.bouvet.kpro.model.Event;
import no.bouvet.kpro.model.Media;
import java.util.List;
import java.util.ArrayList;

import net.ontopia.topicmaps.core.TopicIF;

public class TopicMapMedia extends TopicDAO implements Media {
    private static VaudevilleTopicMap tm;

    private static final ITopicParameter EVENT = new StandardTopicParameter(VaudevilleTopicType.EVENT);

    static {
        tm = new VaudevilleTopicMap("musikk.xtm");
    }

    public TopicMapMedia(String psi) {
        super(tm.getTopicDAOByPSI(psi).getTopicIF());
    }

    public TopicMapMedia(TopicIF topicIF) {
        super(topicIF);
    }

    public List<Event> getEvents() {
        List<Event> result = new ArrayList<Event>();
        TologQuery tologQuery = new TologQuery(VaudevilleAssociationType.MEDIA_EVENT, new StandardTopicParameter(this), EVENT);
        List<TopicIF> topicList = tm.queryForList(tologQuery, EVENT);
        for (TopicIF topicIF : topicList) {
            result.add(new TopicMapEvent(topicIF));
        }
        return result;
    }

    public TopicType getTopicType() {
        return VaudevilleTopicType.MEDIA;
    }

    public String getSubjectLocator() {
        return super.getTopicIF().getSubject().getAddress();
    }
}