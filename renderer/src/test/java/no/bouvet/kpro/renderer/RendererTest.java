package no.bouvet.kpro.renderer;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RendererTest {
	private long _delivered = 0;

	private class TestRenderer extends AbstractRenderer {
		@Override
		public void handleInstruction(int time, Instruction instruction) {
			if (instruction != null) {
				_delivered = System.currentTimeMillis();
			}
		}
	}

	private class TestInstruction extends Instruction {
		public TestInstruction() {
			_start = Renderer.RATE * 2;
			_end = Renderer.RATE * 3;
		}
	}

	@Test
	public void testStart() throws Exception {
		Renderer renderer = null;

		try {
			// Create the renderer instruction list

			Instructions instructions = new Instructions();
			TestInstruction instruction = new TestInstruction();
			instructions.append(instruction);

			// Create the Renderer with a TestRenderer instance

			renderer = new Renderer(instructions);
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
							"Timed out waiting for Renderer to finish");
				}
			}

			// Convert the observed instruction delivery time into a renderer
			// time

			_delivered = (_delivered - start) * Renderer.RATE / 1000;

			// Assert that the delivery occurred within 0.2 seconds of the mark

			assertTrue(Math.abs(_delivered - instruction.getStart()) < Renderer.RATE / 5);
		} finally {
			if (renderer != null)
				renderer.stop();
		}
	}
}
