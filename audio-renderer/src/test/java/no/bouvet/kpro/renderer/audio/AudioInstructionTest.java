package no.bouvet.kpro.renderer.audio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class AudioInstructionTest {
	private AudioInstruction inst;

	@BeforeEach
	public void setUp() throws Exception {
        FileRepresentation testMp3 = Store.get().cache(ClassLoader.getSystemResource("test.mp3"));
		inst = new AudioInstruction(0, 5, 20, 10, testMp3);
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
