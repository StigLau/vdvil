package no.bouvet.kpro.model.stigstest;

import net.ontopia.topicmaps.core.TopicIF;
import no.bouvet.topicmap.query.ITologQuery;
import no.bouvet.topicmap.query.SimpleTologQueryString;
import no.bouvet.topicmap.query.SimpleTopicParameterFactory;
import no.bouvet.topicmap.dao.TopicDAO;
import no.bouvet.kpro.persistence.VaudevilleTopicMap;
import no.bouvet.kpro.model.Lyric;

public class TopicMapLyric extends TopicDAO implements Lyric {

    private static VaudevilleTopicMap tm;
    static {
        tm = new VaudevilleTopicMap("musikk.xtm");
    }

    public TopicMapLyric(TopicIF topicIF) {
        super(topicIF);
    }

    public String getText() {
        ITologQuery tologQuery = new SimpleTologQueryString("vdvil:text(%TOPIC%, $TEXT)?", this, "TOPIC");
        return tm.queryForSingleValue(tologQuery, SimpleTopicParameterFactory.create("TEXT"));
    }
}
