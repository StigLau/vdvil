package no.lau.vdvil.mix;

import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.TestMp3s;
import no.vdvil.renderer.audio.Track;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TimingSyncronizationTest extends SuperPlayingSetup {
    Track returning = TestMp3s.returning;
    @Test
    public void play() {
        super.play(new MasterBeatPattern(0, 8, 130F));
    }
    @Override
    protected Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(createAudioPart(returning.segments.get(3).id, new Interval(0, 4), TestMp3s.returningDvl, downloader));
        parts.add(createAudioPart(returning.segments.get(6).id, new Interval(4, 12), TestMp3s.returningDvl, downloader));
        parts.add(createAudioPart(returning.segments.get(9).id, new Interval(32, 12), TestMp3s.returningDvl, downloader));
        parts.add(createAudioPart(returning.segments.get(10).id, new Interval(48, 12), TestMp3s.returningDvl, downloader));
        
        parts.add(createImagePart("1", new Interval(0,  16), new URL("http://farm6.static.flickr.com/5101/5659214954_5c333c4cd1_d.jpg")));
        parts.add(createImagePart("2", new Interval(8, 16), new URL("http://farm6.static.flickr.com/5181/5659204108_975723a0fe_d.jpg")));
        parts.add(createImagePart("3", new Interval(32, 16), new URL("http://farm6.static.flickr.com/5187/5620387714_f2bb05064b_d.jpg")));
        parts.add(createImagePart("4", new Interval(48, 16), new URL("http://farm6.static.flickr.com/5308/5620385926_1fe89c1011_d.jpg")));
        parts.add(createImagePart("5", new Interval(64, 16), new URL("http://farm6.static.flickr.com/5068/5620372140_6fdf929526_d.jpg")));

        return new Composition(getClass().getSimpleName(), masterBeatPattern, parts, TestMp3s.NULL);
    }
}
