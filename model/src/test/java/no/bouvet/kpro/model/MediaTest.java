package no.bouvet.kpro.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;

public class MediaTest {

	@Test
	public void attributeTest() {
		Media medium = new Media("id0");
		medium.setName("test");
		medium.addPart("id1");
		medium.addPart("id2");

		assertEquals("id0", medium.getId());
		assertEquals("test", medium.getName());
		assertTrue(medium.getParts().contains("id1"));
		assertTrue(medium.getParts().contains("id2"));
		assertTrue(medium.removePart("id1"));
		assertFalse(medium.getParts().contains("id1"));
		assertTrue(medium.removePart("id2"));
		assertFalse(medium.getParts().contains("id2"));
	}

	@Test
	public void compareTest() {
		Media medium1 = new Media("id0");
		Media medium2 = new Media("id1");
		Media medium3 = new Media("id0");
		Media medium4 = new Media("id0");

		assertTrue(medium1.equals(medium3));

		medium1.setName("test");
		medium2.setName("test");
		medium3.setName("sang");
		medium4.setName("test");

		URL url1;
		URL url2;
		try {
			url1 = new URL("http://asdf");
			url2 = new URL("http://qwer");
			medium1.setMediaFile(url1);
			medium2.setMediaFile(url1);
			medium3.setMediaFile(url1);
			medium4.setMediaFile(url2);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		assertFalse(medium1.equals(null));
		assertFalse(medium1.equals(medium2));
		assertFalse(medium1.equals(medium3));
		assertFalse(medium1.equals(medium4));
		medium2.setId("id0");
		assertTrue(medium1.equals(medium2));

	}

}
