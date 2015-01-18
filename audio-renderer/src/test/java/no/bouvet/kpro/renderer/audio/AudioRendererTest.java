package no.bouvet.kpro.renderer.audio;

import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.OldRenderer;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.instruction.Instruction;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudioRendererTest {
    Logger log = LoggerFactory.getLogger(getClass());

	@Test
	public void playingOWherePitchIncreases() {
		AudioTarget target = null;
		OldRenderer renderer = null;

		try {
			// Create the AudioSource

			FileRepresentation fileRepresentation = Store.get().cache(ClassLoader.getSystemResource("test.mp3"));

			// Create the AudioTarget

			target = new AudioPlaybackTarget();

			// Create the renderer instruction list

			Instructions instructions = new Instructions();
			AudioInstruction instruction = new AudioInstruction(0, 206959, 0, Instruction.RESOLUTION, fileRepresentation);
			instruction.setInterpolatedRate(0.1f, 0.4f);
			instruction.setInterpolatedVolume(1.5f, 0.1f);
			instructions.append(instruction);

			// Create the OldRenderer with an AudioRenderer instance

			renderer = new OldRenderer(instructions);
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
		}
	}
}
