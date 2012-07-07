package no.bouvet.kpro.renderer.audio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import java.net.URL;

public class AudioInstructionTest {
	private AudioInstruction inst;
    URL url = getClass().getResource("/test.mp3");

	@Before
	public void setUp() throws Exception {
		inst = new AudioInstruction(0, 5, url, 20, 10);
	}

	@Test
	public void testGetInterpolatedRate() {
		inst.setInterpolatedRate(1, 2);
		assertEquals(66150, inst.getInterpolatedRate(5));
	}

	@Test
	public void testGetInterpolatedVolume() {
		inst.setInterpolatedVolume(1, 2);
		assertEquals(190, inst.getInterpolatedVolume(5));
	}

	@Test
	public void testAdvanceCache() {
		inst.setCacheExternal(100);
		inst.setCacheInternal(200);
		inst.advanceCache(1, 2);
		assertTrue(inst.getCacheExternal() == 101
				&& inst.getCacheInternal() == 202);
	}
}
