package no.bouvet.kpro.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CompositionTest {

	@Test
	public void attributeTest() {
		Composition composition = new Composition("id0");

		composition.setName("test");
		composition.addPerformance("id1");
		composition.addPerformance("id2");

		assertEquals("id0", composition.getId());
		assertEquals("test", composition.getName());

		assertTrue(composition.getPerformances().contains("id1"));
		assertTrue(composition.getPerformances().contains("id2"));
		assertTrue(composition.removePerformance("id1"));
		assertFalse(composition.getPerformances().contains("id1"));
		assertTrue(composition.removePerformance("id2"));
		assertFalse(composition.getPerformances().contains("id2"));
	}

}
