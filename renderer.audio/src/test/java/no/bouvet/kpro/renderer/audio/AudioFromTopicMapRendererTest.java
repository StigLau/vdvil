package no.bouvet.kpro.renderer.audio;

import no.bouvet.kpro.model.stigstest.TopicMapEvent;
import no.bouvet.kpro.renderer.Renderer;
import org.junit.Test;
import org.apache.log4j.Logger;


public class AudioFromTopicMapRendererTest {

    static Logger log = Logger.getLogger(AudioFromTopicMapRendererTest.class);


    @Test
    public void dummyTest() {

    }

    @Test
    // Not ready for full testing harness
    public void testFullRendering() throws Exception {

        //Media media = new Media("https://wiki.bouvet.no/snap-rythm_is_a_dancer");
        AudioTopicMapInstructions instructions = new AudioTopicMapInstructions(new TopicMapEvent("https://wiki.bouvet.no/snap_vs_corona"));

		AudioTarget target = new AudioPlaybackTarget();
		Renderer renderer	= null;

		try {
			log.debug("Duration Time: " + instructions.getDuration());


			// Create the Renderer with an AudioRenderer instance
			renderer = new Renderer(instructions);
			renderer.addRenderer(new AudioRenderer(target));
			//renderer.addRenderer(new LyricRenderer());

			// Start the renderer at the beginning
			log.debug("Starting renderer...");
			renderer.start(0);

			// Wait for the renderer to finish
			while ( renderer.isRendering() ) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {}

				int samples = target.getOutputPosition();
				double percent = Math.floor((double) samples / (double) instructions.getDuration() * 1000) / 10;

				log.debug("Rendered " + samples + " samples (" + percent + "%)...");
			}
			log.debug("Fin");
		} finally {
			if (renderer != null)
				renderer.stop();
			if (target != null)
				target.close();
			instructions.close();
		}
	}
}
