package no.bouvet.kpro.renderer.audio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

public abstract class AudioTargetTest {
	protected AudioTarget target;

	@After
	public void tearDown() throws Exception {
		if (target != null) {
			target.close();
			target = null;
		}
	}

	@Test
	public void testGetBufferDuration() {
		assertTrue(target.getBufferDuration() > 0);
	}

	@Test
	public void testGetWritableDuration() {
		assertTrue(target.getWritableDuration() > 0
				&& target.getWritableDuration() <= target.getBufferDuration());
	}

	@Test
	public void testGetOutputPosition() {
		assertEquals(0, target.getOutputPosition());
	}

	@Test
	public void testWrite() {
		writeSamples();
		target.drain();

		assertEquals(44100, target.getOutputPosition());
	}

	protected void writeSamples() {
		byte[] samples = new byte[44100 * 4];
		float factor = (float) Math.PI * 2.0f / 44.1f;

		for (int sample = 0, offset = 0; sample < 44100; sample++) {
			float time = sample * factor;
			short value = (short) (Math.sin(time) * 32000);

			samples[offset++] = (byte) (value & 0xFF);
			samples[offset++] = (byte) (value >>> 8);
			samples[offset++] = (byte) (value & 0xFF);
			samples[offset++] = (byte) (value >>> 8);
		}

		target.write(samples, 0, 44100);
	}
}
