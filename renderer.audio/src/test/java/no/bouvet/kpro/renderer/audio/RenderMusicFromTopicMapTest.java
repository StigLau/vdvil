package no.bouvet.kpro.renderer.audio;

import no.bouvet.kpro.model.stigstest.Event;
import no.bouvet.kpro.model.stigstest.Media;
import no.bouvet.kpro.renderer.Renderer;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class RenderMusicFromTopicMapTest {

    @Test
    public void dummyTest() {

    }

    //@Test Not ready for full testing harness
    public void testFullRendering() throws Exception {

        //Media media = new Media("https://wiki.bouvet.no/snap-rythm_is_a_dancer");
        Event mainEvent = new Event("https://wiki.bouvet.no/snap_vs_corona");

        TopicMapInstructions instructions = new TopicMapInstructions(mainEvent);

		AudioTarget target = new AudioPlaybackTarget();
		Renderer renderer	= null;

		try {
			System.out.println("Duration Time: " + instructions.getDuration());


			// Create the Renderer with an AudioRenderer instance
			renderer = new Renderer(instructions);
			renderer.addRenderer(new AudioRenderer(target));

			// Start the renderer at the beginning
			System.out.println("Starting renderer...");
			renderer.start(0);

			// Wait for the renderer to finish
			while ( renderer.isRendering() ) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {}

				int samples = target.getOutputPosition();
				double percent = Math.floor((double) samples / (double) instructions.getDuration() * 1000) / 10;

				System.out.println("Rendered " + samples + " samples (" + percent + "%)...");
			}
			System.out.println("Fin");
		} finally {
			if (renderer != null)
				renderer.stop();
			if (target != null)
				target.close();
			instructions.close();
		}
	}
}
