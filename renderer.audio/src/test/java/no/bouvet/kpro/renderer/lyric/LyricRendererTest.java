package no.bouvet.kpro.renderer.lyric;

import org.junit.Test;
import org.apache.log4j.Logger;
import no.bouvet.kpro.model.stigstest.Event;
import no.bouvet.kpro.renderer.Renderer;
import no.bouvet.kpro.renderer.audio.TopicMapInstructions;

public class LyricRendererTest {

    static Logger log = Logger.getLogger(LyricRendererTest.class.getName());

    @Test
    // Not ready for full testing harness
    public void testRenderingText() throws Exception {
        TopicMapInstructions instructions = new TopicMapInstructions(new Event("https://wiki.bouvet.no/snap_vs_corona"));

		//AudioTarget target = new AudioPlaybackTarget();
		Renderer renderer	= null;

		try {
			log.info("Duration Time: " + instructions.getDuration());


			// Create the Renderer with an AudioRenderer instance
			renderer = new Renderer(instructions);
			renderer.addRenderer(new LyricRenderer());

			// Start the renderer at the beginning
			log.info("Starting renderer...");
			renderer.start(0);

			// Wait for the renderer to finish
			while ( renderer.isRendering() ) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {}

				//int samples = target.getOutputPosition();
				//double percent = Math.floor((double) samples / (double) instructions.getDuration() * 1000) / 10;

				//System.out.println("Rendered " + samples + " samples (" + percent + "%)...");
			}
			log.info("Finished rendering");
		} finally {
			if (renderer != null)
				renderer.stop();
			//if (target != null)
			//	target.close();
			instructions.close();
		}
	}
}
