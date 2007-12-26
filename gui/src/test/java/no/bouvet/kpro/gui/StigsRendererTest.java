package no.bouvet.kpro.gui;

import org.junit.Test;


import java.io.File;

import no.bouvet.kpro.persistence.Storage;
import no.bouvet.kpro.model.Media;
import no.bouvet.kpro.renderer.audio.TopicMapInstructions;
import no.bouvet.kpro.renderer.audio.AudioRenderer;
import no.bouvet.kpro.renderer.audio.AudioTarget;
import no.bouvet.kpro.renderer.Renderer;

public class StigsRendererTest {
    private TopicMapInstructions instructions;
    private Renderer renderer;
    private AudioTarget audioTarget;

    @Test
    public void testRendering() throws Exception {
        Media instructionMedia = Storage.getInstance().getMediaByFileName("snap_vs_corona.mp3");

        String path = "/Volumes/McFeasty/Users/Stig/jobb/utvikling/privat/kpro2007/renderer.audio/music/";
        //instructions = new TopicMapInstructions(instructionMedia, new File(path));
		renderer = new Renderer(instructions);
		renderer.addRenderer(new AudioRenderer(audioTarget));
		renderer.start(0);
    }
}
