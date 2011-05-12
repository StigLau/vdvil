package no.bouvet.kpro.renderer.audio;

import java.io.File;

import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;
import org.apache.log4j.Logger;

public abstract class CompositionDemo {
    static Logger log = Logger.getLogger(CompositionDemo.class);

    public static void main(String[] args) throws Exception {
		AudioSource sourceA = null; // First source
		AudioSource sourceB = null; // Second source

		AudioTarget target = null; // Audio output target
		Renderer renderer = null; // Renderer

        String snapMp3 = CompositionDemo.class.getResource("/Snap_-_Rhythm_is_a_Dancer.mp3").getFile();
        String coronaMp3 = CompositionDemo.class.getResource("/Corona_-_Baby_Baby.mp3").getFile();

        try {
            sourceA = AudioSourceFactory.load(new File(snapMp3));
			log.debug("Loaded " + snapMp3 + "Source is " + sourceA.getDuration() + " samples");

			sourceB = AudioSourceFactory.load(new File(coronaMp3));
			log.debug("Loaded " + coronaMp3 + ". Source is " + sourceB.getDuration() + " samples");

            assertAudioSourcesCorrect(sourceA, sourceB);

            Instructions instructions = createTestSetInstructions(sourceA, sourceB);

            // Create the AudioTarget
			log.debug("Creating AudioPlaybackTarget...");
			target = new AudioPlaybackTarget();

			// Create the Renderer with an AudioRenderer instance
			renderer = new Renderer(instructions);
			renderer.addRenderer(new AudioRenderer(target));
            log.debug("Starting renderer...");
            renderer.start(0); // Start the renderer at the beginning
            printStatus(target, renderer, instructions);
        } finally {
			if (renderer != null)
				renderer.stop();
			if (target != null)
				target.close();
			if (sourceB != null)
				sourceB.close();
			if (sourceA != null)
				sourceA.close();
            log.debug("Rendering finished");
        }
	}

    private static void assertAudioSourcesCorrect(AudioSource sourceA, AudioSource sourceB) throws Exception {
        // Check the durations to make sure the correct songs are used
        if (Math.abs(sourceA.getDuration() - 10037376) > 44100) {
            throw new Exception("Source A has the wrong duration, perhaps it is the wrong version of the song");
        } else if (Math.abs(sourceB.getDuration() - 9895680) > 44100) {
            throw new Exception("Source B has the wrong duration, perhaps it is the wrong version of the song");
        }
    }

    private static void printStatus(AudioTarget target, Renderer renderer, Instructions instructions) throws Exception {
        // Wait for the renderer to finish
        long now = System.currentTimeMillis();
        while (renderer.isRendering()) {
            Thread.sleep(500);
            if ((System.currentTimeMillis() - now) > 170000) {
                throw new Exception("Timed out waiting for Renderer to finish");
            }
            int samples = target.getOutputPosition();
            double percent = Math.floor((double) samples / (double) instructions.getDuration() * 1000) / 10;
            log.debug("Rendered " + samples + " samples (" + percent + "%)...");
        }
    }

    /**
     * Create the renderer instruction list using source A and source B
     * This instruction list was created by Mike's DJ Composer, exported
     * and converted into Java setup code.
     * @param sourceA
     * @param sourceB
     * @return
     */
    private static Instructions createTestSetInstructions(AudioSource sourceA, AudioSource sourceB) {
        Instructions instructions = new Instructions();
        AudioInstruction instruction;

        instruction = new AudioInstruction(0, 1406895, sourceA, 555679, 1380735);
        instruction.setConstantRate(0.98154193f);
        instructions.append(instruction);

        instruction = new AudioInstruction(1406895, 2031062, sourceA, 1936416, 612646);
        instruction.setConstantRate(0.98154193f);
        instruction.setInterpolatedVolume(1, 0);
        instructions.append(instruction);

        instruction = new AudioInstruction(1406895, 2031062, sourceB, 3843712, 635672);
        instruction.setConstantRate(1.0184354f);
        instruction.setInterpolatedVolume(0, 1);
        instructions.append(instruction);

        instruction = new AudioInstruction(2031062, 5312932, sourceB, 4479385, 3342150);
        instruction.setConstantRate(1.0184354f);
        instructions.append(instruction);

        instruction = new AudioInstruction(5312932, 5939578, sourceB, 7821536, 638197);
        instruction.setConstantRate(1.0184354f);
        instruction.setInterpolatedVolume(1, 0);
        instructions.append(instruction);

        instruction = new AudioInstruction(5312932, 5939578, sourceA, 8148960, 615078);
        instruction.setConstantRate(0.98154193f);
        instruction.setInterpolatedVolume(0, 1);
        instructions.append(instruction);

        instruction = new AudioInstruction(5939578, 7226900, sourceA, 8764037, 1263386);
        instruction.setConstantRate(0.98154193f);
        instructions.append(instruction);

        log.debug("Instruction queue has expected duration " + instructions.getDuration());
        return instructions;
    }
}
