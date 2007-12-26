package no.bouvet.kpro.renderer.audio;

import org.junit.Before;

public class AudioPlaybackTargetTest extends AudioTargetTest {
	@Before
	public void setUp() throws Exception {
		target = new AudioPlaybackTarget();
	}
}
