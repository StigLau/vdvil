package no.lau.vdvil.rendererv2;

import no.bouvet.kpro.renderer.audio.AudioPlaybackTarget;
import no.bouvet.kpro.renderer.audio.AudioTarget;
import no.lau.vdvil.composition.CompositionAdapter;
import no.lau.vdvil.control.Conductor;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import static no.lau.vdvil.mix.util.SuperPlayingSetup.*;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.renderer.MetronomeRenderer;
import no.lau.vdvil.timing.*;
import no.vdvil.renderer.audio.AudioRendererV2;
import no.vdvil.renderer.audio.TestMp3s;
import no.vdvil.renderer.audio.Track;
import no.vdvil.renderer.image.ImageRenderer;
import no.vdvil.renderer.lyric.LyricRenderer;
import org.junit.Test;
import java.net.MalformedURLException;
import java.io.IOException;
import java.net.URL;

public class RenderingV2Test {
    protected DownloadAndParseFacade downloader = PreconfiguredVdvilPlayer.downloadAndParseFacade;

    @Test
    public void playImageAndLyrics() throws InterruptedException, IOException {
        Clock clock = new SystemClock();
        long origo = clock.getCurrentTimeMillis() + 2000; //Give the app 2 sec to start up
        final RunnableResolutionTimer timer = new RunnableResolutionTimer(clock, origo);
        Conductor conductor = new Conductor(timer, new MasterBeatPattern(0, 20, 130F));
        conductor.addInstruction(new ImageRenderer(800, 600, downloader), CompositionAdapter.cacheAndconvert(downloader, createImageParts()));
        conductor.addInstruction(new LyricRenderer(600, 50), CompositionAdapter.cacheAndconvert(downloader, createLyricParts()));
        MetronomeRenderer metronome = new MetronomeRenderer(0, 20);
        conductor.addInstruction(metronome, metronome.instructions());

        timer.play();
        while (conductor.isPlaying()) {
            Thread.sleep(500);
        }
    }
    @Test
    public void playAudio() throws InterruptedException, IOException {
        Clock clock = new SystemClock();
        long origo = clock.getCurrentTimeMillis() + 2000; //Give the app 2 sec to start up
        final RunnableResolutionTimer timer = new RunnableResolutionTimer(clock, origo);
        Conductor conductor = new Conductor(timer, new MasterBeatPattern(0, 20, 130F));
        //conductor.addInstruction(new ImageRenderer(800, 600, downloader), CompositionAdapter.cacheAndconvert(createImageParts()));
        //conductor.addInstruction(new LyricRenderer(600, 50), CompositionAdapter.cacheAndconvert(createLyricParts()));
        AudioTarget audioTarget = new AudioPlaybackTarget();
        conductor.addInstruction(new AudioRendererV2(audioTarget, 130F), CompositionAdapter.cacheAndconvert(downloader, createAudioParts()));
        MetronomeRenderer metronome = new MetronomeRenderer(0, 20);
        conductor.addInstruction(metronome, metronome.instructions());

        timer.play();
        while (conductor.isPlaying()) {
            Thread.sleep(500);
        }
    }

    private MultimediaPart[] createLyricParts()  {
        return new MultimediaPart[]{
                        createLyricPart("Du ", new Interval(0, 4)),
                        createLyricPart("hasst ", new Interval(4, 4)),
                        createLyricPart("mich ", new Interval(8, 4)),
                        createLyricPart("gefragt", new Interval(12, 4)),
                        createLyricPart(",und ich hab nichts gesagt", new Interval(16, 4))
                };
    }

    private MultimediaPart[] createImageParts()  {
        try {
            return new MultimediaPart[]{
                    createImagePart("1", new Interval(0, 4), new URL("http://farm6.static.flickr.com/5101/5659214954_5c333c4cd1_d.jpg")),
                    createImagePart("2", new Interval(4, 4), new URL("http://farm6.static.flickr.com/5181/5659204108_975723a0fe_d.jpg")),
                    createImagePart("3", new Interval(8, 4), new URL("http://farm6.static.flickr.com/5187/5620387714_f2bb05064b_d.jpg")),
                    createImagePart("4", new Interval(12, 4), new URL("http://farm6.static.flickr.com/5308/5620385926_1fe89c1011_d.jpg")),
                    createImagePart("5", new Interval(16, 4), new URL("http://farm6.static.flickr.com/5068/5620372140_6fdf929526_d.jpg"))
            };
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    Track returning = TestMp3s.returning;

    MultimediaPart[] createAudioParts() {
        return new MultimediaPart[]{
                createAudioPart(returning.segments.get(0).id, new Interval(0, 16), TestMp3s.returningDvl, downloader),
                createAudioPart(returning.segments.get(6).id, new Interval(16, 16), TestMp3s.returningDvl, downloader),
                createAudioPart(returning.segments.get(9).id, new Interval(32, 16), TestMp3s.returningDvl, downloader),
                createAudioPart(returning.segments.get(10).id, new Interval(48, 16), TestMp3s.returningDvl, downloader)
        };
    }
}
