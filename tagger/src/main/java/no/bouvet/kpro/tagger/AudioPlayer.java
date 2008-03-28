package no.bouvet.kpro.tagger;

import no.bouvet.kpro.renderer.audio.AudioTarget;
import no.bouvet.kpro.renderer.audio.AudioPlaybackTarget;
import no.bouvet.kpro.renderer.audio.AudioRenderer;
import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;

public class AudioPlayer {
    Renderer renderer;

    public void playMusic(Instructions instructions) throws Exception {

        AudioTarget target = new AudioPlaybackTarget();
        renderer = new Renderer(instructions);

        renderer.addRenderer(new AudioRenderer(target));

        // Start the renderer at the beginning
        renderer.start(0);

        // Wait for the renderer to finish
        while (renderer.isRendering()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}

            int samples = target.getOutputPosition();
            double percent = Math.floor((double) samples / (double) instructions.getDuration() * 1000) / 10;
        }
    }

    public void stop() {
        renderer.stop();
    }
}
