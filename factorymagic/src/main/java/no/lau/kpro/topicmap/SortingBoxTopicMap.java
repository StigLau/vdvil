package no.lau.kpro.topicmap;

import net.ontopia.topicmaps.entry.TopicMaps;
import net.ontopia.topicmaps.query.core.QueryResultIF;
import net.ontopia.topicmaps.core.TopicIF;
import no.bouvet.topicmap.core.TopicMap;
import no.bouvet.topicmap.core.TopicMapUtil;
import no.bouvet.topicmap.core.RowMapper;
import no.bouvet.topicmap.query.ITologQuery;
import no.bouvet.topicmap.query.SimpleTologQueryString;
import no.lau.kpro.domain.TopicIdentifyable;

/**
 * Author: <a href="mailto:stig@lau.no">Stig Lau</a>
 * Date: Jul 31, 2008
 */

public class SortingBoxTopicMap extends TopicMap {
	static String prefixes = "  using ont   for i\"http://psi.ontopia.net/#\""
			+ "  using onto  for i\"http://psi.ontopia.net/ontology/\""
			+ "  using purl  for i\"http://purl.org/dc/elements/1.1/\""
			+ "  using xtm   for i\"http://www.topicmaps.org/xtm/1.0/core.xtm#\""
			+ "  using tech  for i\"http://www.techquila.com/psi/thesaurus/#\""
			+ "  import \"http://psi.ontopia.net/tolog/string/\" as str "
            + "  using vdvil  for i\"https://wiki.bouvet.no/\"";

	static {
		declarations = prefixes;
	}

	public SortingBoxTopicMap(String file) {
		super(TopicMaps.createStore(file, true));
	}




    public TopicIdentifyable getTopicsByTimeFrame(int start, int stop) {
        String query =
                "select $T from\n" +
                "topic-name($T, $N),\n" +
                "scope($N, short-name),\n" +
                "topic-name($T, $M),\n" +
                "scope($M, short-name),\n" +
                "$N /= $M?";
        ITologQuery iTologQuery = new SimpleTologQueryString(query);
        final TopicIF topicIF = (TopicIF) TopicMapUtil.executeTologQuery(iTologQuery, new RowMapper() {
            Object result;
            public void addRow(QueryResultIF queryResultIF) { result = queryResultIF.getValue(0); }
            public Object getResult() {return result;}
        }, declarationContext, getInterface());

        TopicIdentifyable topicIdentifyable = new TopicIdentifyable() {
            public no.lau.kpro.domain.Topic getTopic() {
                return new no.lau.kpro.domain.Topic(topicIF.getObjectId());
            }
        };
        return topicIdentifyable;
    }

    @Deprecated
    public TopicMap getInstance() {
        throw new RuntimeException("Deprecated");
    }
}
