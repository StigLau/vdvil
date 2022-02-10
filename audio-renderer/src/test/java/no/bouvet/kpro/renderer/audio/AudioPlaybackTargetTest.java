package no.bouvet.kpro.renderer.audio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

@Tag("IntegrationTest")
public class AudioPlaybackTargetTest extends AudioTargetTest {
	@BeforeEach
	public void setUp() {
		target = new AudioPlaybackTarget();
	}
}
