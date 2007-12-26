package no.bouvet.kpro.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CuePointTest {

	@Test
	public void attributeTest() {
		CuePoint cue = new CuePoint(2000);
		cue.setSampleRate(1.0);
		cue.setVolume(100);

		assertEquals(2000, cue.getTime());
		assertEquals(100, cue.getVolume());
	}
}
