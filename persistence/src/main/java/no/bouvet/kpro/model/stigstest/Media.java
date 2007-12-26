package no.bouvet.kpro.model.stigstest;

import no.bouvet.topicmap.dao.TopicDAO;
import no.bouvet.topicmap.dao.TopicType;
import no.bouvet.topicmap.query.TologQuery;
import no.bouvet.topicmap.query.StandardTopicParameter;
import no.bouvet.topicmap.query.ITopicParameter;
import no.bouvet.kpro.persistence.VaudevilleTopicMap;
import no.bouvet.kpro.persistence.VaudevilleAssociationType;
import no.bouvet.kpro.persistence.VaudevilleTopicType;
import java.util.List;
import java.util.ArrayList;

import net.ontopia.topicmaps.core.TopicIF;

public class Media extends TopicDAO {
    public String psi;

    public String uri;

    private static VaudevilleTopicMap tm;

    private static final ITopicParameter EVENT = new StandardTopicParameter(VaudevilleTopicType.EVENT);

    static {
        tm = new VaudevilleTopicMap("musikk.xtm");
    }

    public Media(String mediaFile) {
        super(tm.getTopicDAOByPSI(mediaFile).getTopicIF());
        this.psi = mediaFile;
    }

    public Media(TopicIF topicIF) {
        super(topicIF);
    }

    public List<Event> getEvents() {
        List<Event> result = new ArrayList<Event>();
        TologQuery tologQuery = new TologQuery(VaudevilleAssociationType.MEDIA_EVENT, new StandardTopicParameter(this), EVENT);
        List<TopicIF> topicList = tm.queryForList(tologQuery, EVENT);
        for (TopicIF topicIF : topicList) {
            result.add(new Event(topicIF));
        }
        return result;
    }

    public TopicType getTopicType() {
        return VaudevilleTopicType.MEDIA;
    }
}