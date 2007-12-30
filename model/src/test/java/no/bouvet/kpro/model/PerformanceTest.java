package no.bouvet.kpro.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import no.bouvet.kpro.model.old.CuePoint;
import no.bouvet.kpro.model.old.Performance;

public class PerformanceTest {

	@Test
	public void attributeTest() {
		Performance performance = new Performance("id0");

		CuePoint cue1 = new CuePoint(0);
		CuePoint cue2 = new CuePoint(500);
		CuePoint cue3 = new CuePoint(100000);
		CuePoint cue4 = new CuePoint(102000);

		performance.setMedia("id1");
		performance.setLeadInStart(cue1);
		performance.setLeadInStop(cue2);
		performance.setLeadOutStart(cue3);
		performance.setLeadOutStop(cue4);

		assertEquals("id0", performance.getId());
		assertEquals("id1", performance.getMedia());
		assertEquals(cue1, performance.getLeadInStart());
		assertEquals(cue2, performance.getLeadInStop());
		assertEquals(cue3, performance.getLeadOutStart());
		assertEquals(cue4, performance.getLeadOutStop());
	}

}
