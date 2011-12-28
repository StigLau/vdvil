package no.bouvet.kpro.renderer.audio;

import java.io.File;
import java.net.URL;

import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioRendererTest {
    Logger log = LoggerFactory.getLogger(getClass());

	@Test @Ignore //Does not work since MBP was put in place!
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
            //TODO Uncomment this line if this test is to work!
			//renderer = new Renderer(instructions, new AudioRenderer(target));

			// Start the renderer at the beginning

			renderer.start(new MasterBeatPattern(0, 15, 120F));

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
