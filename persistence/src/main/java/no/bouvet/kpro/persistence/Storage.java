package no.bouvet.kpro.persistence;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.ontopia.infoset.core.LocatorIF;
import net.ontopia.topicmaps.core.TopicIF;
import net.ontopia.topicmaps.impl.basic.BaseName;
import net.ontopia.topicmaps.impl.basic.Occurrence;
import net.ontopia.topicmaps.query.core.InvalidQueryException;
import net.ontopia.topicmaps.query.core.QueryProcessorIF;
import net.ontopia.topicmaps.query.core.QueryResultIF;
import net.ontopia.topicmaps.query.utils.QueryUtils;
import no.bouvet.kpro.model.Media;
import no.bouvet.kpro.model.Part;
import no.bouvet.topicmap.core.TopicMap;
import org.apache.log4j.Logger;

public class Storage implements VaudevilleStorage {
	
	private static Storage instance;
	
	private TopicMap topicMap;
	private QueryProcessorIF processor; 
	private static String file = null;

    static Logger log = Logger.getLogger(Storage.class);

	private Storage() {
		setUp();
	}
	
	public static void setFile(String name){
		file = name;
	}
	private void setUp(){
		VaudevilleTopicMap map = null;
		if (file != null){
			map = new VaudevilleTopicMap(file);
		}else{
			map = new VaudevilleTopicMap("eventdrivenmusicmeta.ltm");
		}
		VaudevilleServlet.setTopicMap(map);
		topicMap = VaudevilleServlet.getTopicMap();
		processor = QueryUtils.getQueryProcessor(topicMap.getInterface());
	}
	
	private void tearDown(){
		topicMap.getInterface().getStore().close();
		VaudevilleServlet.setTopicMap(null);
	}
	
	/**
	 * Access point for demonstration of the persistence module stand alone
	 * 
	 * @param args - command line parameters, non used so far
	 */
	public static void main(String[] args) {
		for(Media m: getInstance().getAllMedia()){
			System.out.println(m.getName()+", " + m.getMediaFile()+ ", " + m.getId());
		}
	}

	public Set<Media> getAllMedia() {
		Set<Media> media = null;
		try {
			QueryResultIF result = processor.execute("instance-of($MEDIA, media)?");
			media = mediaFactory(result);
		} catch (InvalidQueryException e) {
			e.printStackTrace();
		}
		return media;
	}

	public List<Part> getChildren(Media medium) {
		List<Part> parts = null;
		try {
			QueryResultIF result = processor.execute("part-of(s\""+ medium.getId() +"\" : media, $PART : part), " +
													"start-time($PART, $START) order by $START asc??");
			parts = partsFactory(result);
		} catch (InvalidQueryException e) {
			e.printStackTrace();
		}
		return parts;
	}
	private List<Part> partsFactory(QueryResultIF result){
		List<Part> parts = new ArrayList<Part>();
		while (result.next()){
			TopicIF partIF = (TopicIF)result.getValue("PART");
			Part part = new Part(((LocatorIF)partIF.getSourceLocators().iterator().next()).getAddress());
			part.setName(((BaseName)partIF.getBaseNames().iterator().next()).getValue());
			
			for (Occurrence occ: (Iterable<Occurrence>)partIF.getOccurrences()){
				//TODO: Should check occurrence type by PSI instead of name
				String type = ((BaseName)occ.getType().getBaseNames().iterator().next()).getValue();
				if ("start time".equals(type)){
					part.setStartTime((int) Math.round(Double.valueOf(occ.getValue())));
				}else if ("stop time".equals(type)){
					part.setStopTime((int) Math.round(Double.valueOf(occ.getValue())));
				}else if ("Description".equals(type)){
					part.setDescription(occ.getValue());
				}
			}
			parts.add(part);
		}
		return parts;
	}

	public Media getMediaByFileName(String filename) {
		Media medium = null;
		try {
			QueryResultIF result = processor.execute(VaudevilleTopicMap.prefixes + 
													 "SELECT $MEDIA FROM "+
													 "instance-of($MEDIA, media), "+
													 "occurrence($MEDIA, $OC), "+
													 "resource($OC, $RES), "+
													 "str:ends-with($RES, \"/"+ filename+"\")?");
			Set<Media> media = mediaFactory(result);
			if (media.size() == 1)
				medium = media.iterator().next();			
		} catch (InvalidQueryException e) {
			e.printStackTrace();
		}		
		return medium;
	}
	
	private Set<Media> mediaFactory(QueryResultIF result){
		HashSet<Media> media = new HashSet<Media>();
		while (result.next()){
			TopicIF mediumIF = (TopicIF)result.getValue("MEDIA");
			Media medium = new Media(((LocatorIF)mediumIF.getSourceLocators().iterator().next()).getAddress());
			medium.setName(((BaseName)mediumIF.getBaseNames().iterator().next()).getValue());
			
			for (Occurrence occ: (Iterable<Occurrence>)mediumIF.getOccurrences()){
				//TODO: Should check occurrence type by PSI instead of name
				if ("mediafile".equals(((BaseName)occ.getType().getBaseNames().iterator().next()).getValue())){
					try {
						medium.setMediaFile(new URL(occ.getLocator().getAddress()));
					} catch (MalformedURLException e) {
						log.error("Bad URL in topicmap or tried reading wrong occurence");
						e.printStackTrace();
					}
				}					
			}
			media.add(medium);
		}
		return media;
	}

	public Media getMediaByURI(URI uri) {
		Media medium = null;
		try {
			QueryResultIF result = processor.execute(VaudevilleTopicMap.prefixes+
													 " SELECT $MEDIA FROM "+
													 "instance-of($MEDIA, media), "+
													 "occurrence($MEDIA, $OC), "+
													 "resource($OC, $RES), "+
													 "$RES = \""+ uri +"\"?");
			Set<Media> media = mediaFactory(result);
			if (media.size() == 1)
				medium = media.iterator().next();			
		} catch (InvalidQueryException e) {
			e.printStackTrace();
		}		
		return medium;
	}

	public List<Part> getChildren(String partId) {
		List<Part> parts = new ArrayList<Part>();
		try {
			QueryResultIF result = processor.execute("contains(s\""+ partId +"\" : container, $PART : containee), " +
													 "start-time($PART, $START) order by $START asc?");
			parts.addAll(partsFactory(result));
		} catch (InvalidQueryException e) {
			e.printStackTrace();
		}
		return parts;
	}

	public static Storage getInstance() {
		if (instance == null){
			instance = new Storage();
		}
		return instance;
	}

	public static void close(){
		if (instance != null){
			instance.tearDown();
			instance = null;
		}
	}
	
	public String getTopicMapID(){
		return topicMap.getInterface().getStore().getBaseAddress().getAddress();
	}
}
