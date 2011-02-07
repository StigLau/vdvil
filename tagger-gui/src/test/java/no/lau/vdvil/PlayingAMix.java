package no.lau.vdvil;

import com.thoughtworks.xstream.XStream;
import no.bouvet.kpro.tagger.PlayStuff;
import no.lau.tagger.model.*;
import org.codehaus.httpcache4j.cache.VdvilCacheStuff;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PlayingAMix {

    VdvilCacheStuff cache = new VdvilCacheStuff(new File("/tmp/vdvil"));
    String holdenUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl";
    String psylteUrl = "http://github.com/StigLau/vdvil/raw/master/tagger-gui/src/test/resources/loaderror-psylteflesk.dvl";

    @Test
    public void testMixing() throws FileNotFoundException, InterruptedException {
        SimpleSong nothing = createSimpleSongFromXML(cache.fetchAsStream(holdenUrl));
        SimpleSong psylteFlesk = createSimpleSongFromXML(cache.fetchAsStream(psylteUrl));
        List<AudioPart> parts = testPlayingSomeStuff(nothing, psylteFlesk);
        PlayStuff playStuff = new PlayStuff(new Composition(130F, parts), cache);
        playStuff.play(4F);
        Thread.sleep(4*1000*130/60);
        playStuff.stop();
        Thread.sleep(50);
    }


    private SimpleSong createSimpleSongFromXML(InputStream inputStream) {
        XStream xstream = new XStream();
        xstream.alias("track", SimpleSong.class);
        xstream.alias("segment", Segment.class);
        return (SimpleSong) xstream.fromXML(inputStream);
    }

    public List<AudioPart> testPlayingSomeStuff(SimpleSong nothing, SimpleSong psylteFlesk) {
        List<AudioPart> parts = new ArrayList<AudioPart>();
        parts.add(new AudioPart(nothing, 0F, 20F, nothing.segments.get(3)));
        parts.add(new AudioPart(psylteFlesk, 8F, 16F, psylteFlesk.segments.get(4)));
        parts.add(new AudioPart(nothing, 12F, 52F, nothing.segments.get(6)));
        parts.add(new AudioPart(nothing, 19.99F, 32F, nothing.segments.get(3)));
        return parts;
    }
}


