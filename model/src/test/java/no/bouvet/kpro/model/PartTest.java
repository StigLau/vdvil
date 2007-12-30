package no.bouvet.kpro.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import no.bouvet.kpro.model.old.Part;

public class PartTest {

	@Test
	public void attributeTest() {
		Part part = new Part("id0");

		part.setName("test");
		part.setStartTime(40000);
		part.setStopTime(100000);
		part.addSubPart("id1");
		part.addSubPart("id2");
		part.setParentPart("id3");
		part.setDescription("description");

		assertEquals("id0", part.getId());
		assertEquals("test", part.getName());
		assertEquals("id3", part.getParentPart());
		assertEquals("description", part.getDescription());
		assertEquals(40000, part.getStartTime());
		assertEquals(100000, part.getStopTime());

		assertTrue(part.getSubParts().contains("id1"));
		assertTrue(part.getSubParts().contains("id2"));
		assertTrue(part.removeSubPart("id1"));
		assertFalse(part.getSubParts().contains("id1"));
		assertTrue(part.removeSubPart("id2"));
		assertFalse(part.getSubParts().contains("id2"));
	}

}
