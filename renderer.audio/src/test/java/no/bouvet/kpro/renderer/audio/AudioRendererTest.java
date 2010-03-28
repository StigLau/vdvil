package no.bouvet.kpro.renderer.audio;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;

import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;

import org.apache.log4j.Logger;
import org.junit.Test;

public class AudioRendererTest {
    static Logger log = Logger.getLogger(AudioRendererTest.class);

	@Test
	public void test() {
		AudioSource source = null;
		AudioTarget target = null;
		Renderer renderer = null;

		try {
			// Create the AudioSource

			URL url = getClass().getResource("/test.mp3");
			source = AudioSourceFactory.load(new File(url.getFile()));

			// Create the AudioTarget

			target = new AudioPlaybackTarget();

			// Create the renderer instruction list

			Instructions instructions = new Instructions();
			AudioInstruction instruction = new AudioInstruction(0, 206959,
					source, 0, Renderer.RATE);
			instruction.setInterpolatedRate(0.1f, 0.4f);
			instruction.setInterpolatedVolume(1.5f, 0.1f);
			instructions.append(instruction);

			// Create the Renderer with an AudioRenderer instance

			renderer = new Renderer(instructions);
			renderer.addRenderer(new AudioRenderer(target));

			// Start the renderer at the beginning

			renderer.start(0);

			// Wait for the renderer to finish

			for (long now = System.currentTimeMillis(); renderer.isRendering();) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}

				if ((System.currentTimeMillis() - now) > 10000) {
					throw new Exception(
							"Timed out waiting for Renderer to finish");
				}
			}

			// Check the number of samples output matches the end of the last
			// instruction

			assertEquals(instructions.getDuration(), target.getOutputPosition());
        } catch (Exception e) {
            log.error(e);
        } finally {
			if (renderer != null)
				renderer.stop();
			if (target != null)
				target.close();
			if (source != null)
				source.close();
		}
	}
}
