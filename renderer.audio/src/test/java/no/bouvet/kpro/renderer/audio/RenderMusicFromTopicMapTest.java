package no.bouvet.kpro.renderer.audio;

import no.bouvet.kpro.model.Media;
import no.bouvet.kpro.model.stigstest.Event;
import no.bouvet.kpro.persistence.Storage;
import no.bouvet.kpro.renderer.Renderer;

import java.net.URI;
import java.io.File;

import org.junit.Test;


public class RenderMusicFromTopicMapTest {

    @Test
    public void testFullRendering() throws Exception {

		Media media = Storage.getInstance().getMediaByURI( new URI( "file://myMusic/snap_vs_corona.mp3" ) );

		TopicMapInstructions instructions = new TopicMapInstructions(new Event(), new File( "/Volumes/McFeasty/Users/Stig/jobb/utvikling/privat/kpro2007/renderer.audio/music/" ) );

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
