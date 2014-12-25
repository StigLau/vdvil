package no.bouvet.kpro.renderer;

import static org.junit.Assert.assertTrue;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.instruction.SuperInstruction;
import org.junit.Test;

public class RendererTest {
	private long _delivered = 0;

	private class TestRenderer extends AbstractRenderer {
        public void notify(Instruction instruction, long time) {
			if (instruction != null) {
				_delivered = System.currentTimeMillis();
			}
		}

        @Override
        public boolean isRendering() {
            return false;
        }

        @Override
        public void stop(Instruction instruction) { }
    }

	@Test
	public void testStart() throws Exception {
		OldRenderer renderer = null;

		try {
			// Create the renderer instruction list

			Instructions instructions = new Instructions();
            Instruction instruction = new SuperInstruction(Instruction.RESOLUTION * 2, Instruction.RESOLUTION * 3, FileRepresentation.NULL) { };
			instructions.append(instruction);

			// Create the OldRenderer with a TestRenderer instance

			renderer = new OldRenderer(instructions);
			renderer.addRenderer(new TestRenderer());

			// Start the renderer at the beginning

			renderer.start(0);

			// Wait for the renderer to finish

			long start = System.currentTimeMillis();

			while (renderer.isRendering()) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}

				if ((System.currentTimeMillis() - start) > 5000) {
					throw new Exception(
							"Timed out waiting for OldRenderer to finish");
				}
			}

			// Convert the observed instruction delivery time into a renderer
			// time

			_delivered = (_delivered - start) * Instruction.RESOLUTION / 1000;

			// Assert that the delivery occurred within 0.2 seconds of the mark

			assertTrue(Math.abs(_delivered - instruction.start()) < Instruction.RESOLUTION / 5);
		} finally {
			if (renderer != null)
				renderer.stop();
		}
	}
}