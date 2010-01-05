package no.bouvet.kpro.persistence;

import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.entry.TopicMaps;
import no.bouvet.topicmap.core.TopicMap;
import no.bouvet.topicmap.dao.TopicDAO;

public class VaudevilleTopicMap extends TopicMap {
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

	public VaudevilleTopicMap(TopicMapStoreIF storeIF) {
		super(storeIF);
	}

	public VaudevilleTopicMap(String file) {
		super(TopicMaps.createStore(file, true));
		TopicMap.topicTypes = VaudevilleTopicType.values();
	}

	@Override
	public TopicMap getInstance() {
		return VaudevilleServlet.getTopicMap();
	}

    public <T> T getTheTopicOfYourDreams(String topicId) {
        TopicDAO topicDAO = super.getTopicDAOByPSI(topicId);
        
        return (T) new Object();
    }

}
