package no.bouvet.kpro;

import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.Renderer;
import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.vdvil.renderer.lyric.LyricRenderer;
import no.vdvil.renderer.lyric.LyricPart;
import no.bouvet.kpro.renderer.audio.*;
import no.bouvet.kpro.tagger.PlayStuff;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.AbstractPart;
import no.lau.tagger.model.AudioPart;
import no.lau.tagger.model.SimpleSong;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple test for setting up a test of lyric/GUI and music
 */
public class AudioAndLyricsExample {
    Logger log = LoggerFactory.getLogger(AudioAndLyricsExample.class);

    public static void main(String[] args) throws Exception {
        new AudioAndLyricsExample().setUpStuff();
    }

    private void setUpStuff() throws Exception {
        Composition composition = new Composition(135F, parts());
        Instructions instructions = new PlayStuff(composition).createInstructionsFromParts(composition);

        AudioTarget target = new AudioPlaybackTarget();
        Renderer renderer = null;



        try {
            log.info("Duration Time: " + instructions.getDuration() / Renderer.RATE + " seconds");

            // Create the Renderer with an AudioRenderer instance
            renderer = new Renderer(instructions);
            renderer.addRenderer(new AudioRenderer(target));
            renderer.addRenderer(new LyricRenderer(1000, 100));

            // Start the renderer at the beginning
            log.info("Starting renderer...");
            renderer.start(0);

            // Wait for the renderer to finish
            while (renderer.isRendering()) {
                Thread.sleep(2000);


                int samples = target.getOutputPosition();
                double percent = Math.floor((double) samples / (double) instructions.getDuration() * 1000) / 10;

                log.info("Rendered " + samples + " samples (" + percent + "%)...");
            }
            log.info("Fin");
        } finally {
            if (renderer != null)
                renderer.stop();
            target.close();
        }
    }

    public static List<? extends AbstractPart> parts() throws FileNotFoundException {
        SimpleSong returning = new XStreamParser().load(VdvilHttpCache.create().fetchAsStream("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"));
        List<AbstractPart> parts = new ArrayList<AbstractPart>();
        parts.add(new AudioPart(returning, 0, 16, returning.segments.get(3)));
        parts.add(new LyricPart("Hello World!", 0, 12));
        parts.add(new AudioPart(returning, 12, 32, returning.segments.get(6)));
        parts.add(new LyricPart("Stig er kul!", 12, 32));
        parts.add(new AudioPart(returning, 32, 62, returning.segments.get(9)));
        parts.add(new LyricPart("And so on!", 32, 62));
        parts.add(new AudioPart(returning, 62, 63, returning.segments.get(10)));
        parts.add(new AudioPart(returning, 63, 64, returning.segments.get(11)));
        parts.add(new AudioPart(returning, 64, 128, returning.segments.get(12)));
        parts.add(new AudioPart(returning, 128, 256, returning.segments.get(14)));
        return parts;
    }
}
