package no.bouvet.kpro.persistence;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.ontopia.topicmaps.core.TopicMapStoreIF;
import net.ontopia.topicmaps.entry.TopicMaps;
import net.ontopia.topicmaps.nav2.utils.ThreadLocalStoreServletFilter;
import no.bouvet.topicmap.core.TopicMap;

import org.apache.log4j.Logger;

public class VaudevilleServlet extends ThreadLocalStoreServletFilter {

	static Logger log = Logger
			.getLogger(ThreadLocalStoreServletFilter.class.getName());

	private static ThreadLocal<TopicMap> data = new ThreadLocal<TopicMap>();

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String repositoryId = getRepositoryId(request);
		String topicMapId = getTopicMapId(request);
		boolean readOnly = getReadOnly(request);

		TopicMapStoreIF store;
		if (repositoryId == null)
			store = TopicMaps.createStore(topicMapId, readOnly);
		else
			store = TopicMaps.createStore(topicMapId, readOnly,
					repositoryId);

		try {
			log.debug("ThreadLocal setting up topicmap");
			data.set(new VaudevilleTopicMap(store));
			chain.doFilter(request, response);
			if (!readOnly)
				store.commit();
		} catch (Exception e) {
			if (!readOnly)
				store.abort();
			log.error("Exception thrown from doFilter.", e);
		} finally {
			log.debug("ThreadLocal closing topicmap");
			data.set(null);
			store.close();
		}
	}


	public static TopicMap getTopicMap() {
		return data.get();
	}

	public static void setTopicMap(TopicMap topicMap) {
		data.set(topicMap);
	}

}
