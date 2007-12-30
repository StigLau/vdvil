package no.bouvet.kpro.model.stigstest;

import net.ontopia.topicmaps.core.TopicIF;
import no.bouvet.topicmap.query.ITologQuery;
import no.bouvet.topicmap.query.SimpleTologQueryString;
import no.bouvet.topicmap.query.SimpleTopicParameterFactory;
import no.bouvet.topicmap.dao.TopicDAO;
import no.bouvet.kpro.persistence.VaudevilleTopicMap;

public class Lyric extends TopicDAO {

    private static VaudevilleTopicMap tm;
    static {
        tm = new VaudevilleTopicMap("musikk.xtm");
    }

    public Lyric(TopicIF topicIF) {
        super(topicIF);
    }

    public String getText() {
        ITologQuery tologQuery = new SimpleTologQueryString("vdvil:text(%TOPIC%, $TEXT)?", this, "TOPIC");
        return tm.queryForSingleValue(tologQuery, SimpleTopicParameterFactory.create("TEXT"));
    }
}
