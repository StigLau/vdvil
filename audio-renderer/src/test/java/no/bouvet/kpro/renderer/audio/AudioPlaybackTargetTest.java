package no.bouvet.kpro.renderer.audio;

import no.lau.IntegrationTest;
import org.junit.Before;
import org.junit.experimental.categories.Category;

@Category(IntegrationTest.class)
public class AudioPlaybackTargetTest extends AudioTargetTest {
	@Before
	public void setUp() {
		target = new AudioPlaybackTarget();
	}
}
