package no.bouvet.kpro.renderer.audio;

import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.OldRenderer;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.instruction.Instruction;
import no.vdvil.renderer.audio.TestMp3s;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CompositionDemo {
    static Logger log = LoggerFactory.getLogger(CompositionDemo.class);
    //TODO Wrong address to mp3's. Does this test have any value?
    public static void main(String[] args) throws Exception {
        //FileRepresentation snap = store.cache(ClassLoader.getSystemResource("Snap_-_Rhythm_is_a_Dancer.mp3"));
        //FileRepresentation corona = store.cache(ClassLoader.getSystemResource("Corona_-_Baby_Baby.mp3"));
        FileRepresentation corona = Store.get().cache(TestMp3s.coronamp3);
		AudioSource sourceA = new MP3Source(corona);
		AudioSource sourceB = new MP3Source(corona);

		AudioTarget target = null; // Audio output target
		OldRenderer renderer = null; // OldRenderer

        try {
			log.debug("Loaded " + sourceA + "Source is " + sourceA.getDuration() + " samples");
			log.debug("Loaded " + sourceB + "Source is " + sourceB.getDuration() + " samples");

            assertAudioSourcesCorrect(sourceA, sourceB);

            Instructions instructions = createTestSetInstructions(corona, corona);

            // Create the AudioTarget
			log.debug("Creating AudioPlaybackTarget...");
			target = new AudioPlaybackTarget();

			// Create the OldRenderer with an AudioRenderer instance
			renderer = new OldRenderer(instructions);
			renderer.addRenderer(new AudioRenderer(target));
            log.debug("Starting renderer...");
            renderer.start(0); // Start the renderer at the beginning
            printStatus(target, renderer, instructions);
        } finally {
			if (renderer != null)
				renderer.stop();
			if (target != null)
				target.close();
            sourceB.close();
            sourceA.close();
            log.debug("Rendering finished");
        }
	}

    private static void assertAudioSourcesCorrect(AudioSource sourceA, AudioSource sourceB) throws Exception {
        // Check the durations to make sure the correct songs are used
        if (Math.abs(sourceA.getDuration() - 10037376) > Instruction.RESOLUTION) {
            throw new Exception("Source A has the wrong duration, perhaps it is the wrong version of the song");
        } else if (Math.abs(sourceB.getDuration() - 9895680) > Instruction.RESOLUTION) {
            throw new Exception("Source B has the wrong duration, perhaps it is the wrong version of the song");
        }
    }

    private static void printStatus(AudioTarget target, OldRenderer renderer, Instructions instructions) throws Exception {
        // Wait for the renderer to finish
        long now = System.currentTimeMillis();
        while (renderer.isRendering()) {
            Thread.sleep(500);
            if ((System.currentTimeMillis() - now) > 170000) {
                throw new Exception("Timed out waiting for OldRenderer to finish");
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
     */
    private static Instructions createTestSetInstructions(FileRepresentation sourceA, FileRepresentation sourceB) {
        Instructions instructions = new Instructions();
        AudioInstruction instruction;

        instruction = new AudioInstruction(0, 1406895, 555679, 1380735, sourceA);
        instruction.setConstantRate(0.98154193f);
        instructions.append(instruction);

        instruction = new AudioInstruction(1406895, 2031062, 1936416, 612646, sourceA);
        instruction.setConstantRate(0.98154193f);
        instruction.setInterpolatedVolume(1, 0);
        instructions.append(instruction);

        instruction = new AudioInstruction(1406895, 2031062, 3843712, 635672, sourceB);
        instruction.setConstantRate(1.0184354f);
        instruction.setInterpolatedVolume(0, 1);
        instructions.append(instruction);

        instruction = new AudioInstruction(2031062, 5312932, 4479385, 3342150, sourceB);
        instruction.setConstantRate(1.0184354f);
        instructions.append(instruction);

        instruction = new AudioInstruction(5312932, 5939578, 7821536, 638197, sourceB);
        instruction.setConstantRate(1.0184354f);
        instruction.setInterpolatedVolume(1, 0);
        instructions.append(instruction);

        instruction = new AudioInstruction(5312932, 5939578, 8148960, 615078, sourceA);
        instruction.setConstantRate(0.98154193f);
        instruction.setInterpolatedVolume(0, 1);
        instructions.append(instruction);

        instruction = new AudioInstruction(5939578, 7226900, 8764037, 1263386, sourceA);
        instruction.setConstantRate(0.98154193f);
        instructions.append(instruction);

        log.debug("AbstractInstruction queue has expected duration " + instructions.getDuration());
        return instructions;
    }
}
