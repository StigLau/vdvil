package no.bouvet.kpro.persistence;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import no.bouvet.kpro.model.old.Media;
import no.bouvet.kpro.model.old.Part;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageTest {

	private static Storage store;
	private static Set<Media> originalMedia;
	private static Media baby;
	private static Media hitme;
	private static Part part;
	private static Part subpart;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Storage.setFile("junit.ltm");
		store = Storage.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Storage.close();
	}

	@Before
	public void setUp() throws Exception {
		originalMedia = new HashSet<Media>();
		hitme = new Media(store.getTopicMapID()+"#hit_me");
		hitme.setName("Hit me baby one more time");
		hitme.setMediaFile(new URL("http://wiki.bouvet.no/hitme.mp3"));
		originalMedia.add(hitme);
		baby = new Media(store.getTopicMapID()+"#babybaby");
		baby.setName("oh baby baby");
		baby.setMediaFile(new URL("http://wiki.bouvet.no/babybaby.mp3"));
		originalMedia.add(baby);
		
		part = new Part(store.getTopicMapID() + "#id52");
		part.setName("Verse1");
		part.setStartTime(8000);
		part.setStopTime(20000);
		
		subpart = new Part(store.getTopicMapID() + "#id1");
		subpart.setName("is killing me");
		subpart.setStartTime(11000);
		subpart.setStopTime(16000);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void getAllMediaTest(){
		Set<Media> media = store.getAllMedia();
		assertTrue("For fŒ media-instanser i topicmappet", media.size() >= 2);
		for (Media om: originalMedia){
			boolean found = false;
			for (Media tm: media){
				if (om.equals(tm)){
					found = true;
					break;
				}
			}
			assertTrue("Fant ikke: "+om.getName(), found);
		}
	}
	
	@Test
	public void getChildrenforMediaTest(){
		boolean found = false;
		for (Part p : store.getChildren(hitme)){
			if (part.equals(p)){
				found = true;
				break;
			}
		}
		assertTrue("Rett part ikke funnet for hitme", found);
	}
	@Test
	public void getChildrenbyPartIDTest(){
		boolean found = false;
		for (Part p : store.getChildren(part.getId())){
			if (subpart.equals(p)){
				found = true;
				break;
			}
		}
		assertTrue("Rett part ikke funnet for hitme", found);
	}
	
	@Test
	public void getMediaByFileNameTest(){
		assertNull("Skal ikke returnere f¿rste og beste dersom filnavnet bare er en delivs match", store.getMediaByFileName("mp3"));
		
		assertTrue("Feil i uthenting av media etter filnavn", baby.equals(store.getMediaByFileName("babybaby.mp3")));
		assertTrue("Feil i uthenting av media etter filnavn", hitme.equals(store.getMediaByFileName("hitme.mp3")));
	}
	@Test
	public void getMediaByURITest() throws URISyntaxException{
		for (Media om : originalMedia){
			assertTrue("Feil i uthenting av media etter URI", om.equals(store.getMediaByURI(om.getMediaFile().toURI())));
		}
			
	}
}
