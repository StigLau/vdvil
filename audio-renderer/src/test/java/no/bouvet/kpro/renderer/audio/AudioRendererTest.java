package no.bouvet.kpro.renderer.audio;

import java.io.File;
import java.net.URL;

import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioRendererTest {
    Logger log = LoggerFactory.getLogger(getClass());

	@Test
	public void playingOWherePitchIncreases() {
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
            long now = System.currentTimeMillis();
			while(renderer.isRendering() && (System.currentTimeMillis() - now) < 6000) {
                //This should play a sound
			}

			// Check the number of samples output matches the end of the last
			// instruction

			//assert(target.getOutputPosition() > instructions.getDuration());
        } catch (Exception e) {
            log.error("Some problem - TODO Bad errormessage!", e);
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
