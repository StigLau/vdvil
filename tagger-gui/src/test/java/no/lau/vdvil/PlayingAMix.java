package no.lau.vdvil;

import no.bouvet.kpro.tagger.PlayStuff;
import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.lau.tagger.model.*;
import static org.codehaus.httpcache4j.cache.VdvilCacheStuff.*;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PlayingAMix {

    String holdenUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl";
    String psylteUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/loaderror-psylteflesk.dvl";

    @Test
    public void testMixing() throws FileNotFoundException, InterruptedException {
        SimpleSong nothing = new XStreamParser().load(fetchAsStream(holdenUrl));
        SimpleSong psylteFlesk = new XStreamParser().load(fetchAsStream(psylteUrl));
        List<AudioPart> parts = testPlayingSomeStuff(nothing, psylteFlesk);
        PlayStuff playStuff = new PlayStuff(new Composition(130F, parts));
        playStuff.play(4F);
        Thread.sleep(4*1000*130/60);
        playStuff.stop();
        Thread.sleep(50);
    }

    public List<AudioPart> testPlayingSomeStuff(SimpleSong nothing, SimpleSong psylteFlesk) {
        List<AudioPart> parts = new ArrayList<AudioPart>();
        parts.add(new AudioPart(nothing, 0, 20, nothing.segments.get(3)));
        parts.add(new AudioPart(psylteFlesk, 8, 16, psylteFlesk.segments.get(4)));
        parts.add(new AudioPart(nothing, 12, 52, nothing.segments.get(6)));
        parts.add(new AudioPart(nothing, 20, 32, nothing.segments.get(3)));
        return parts;
    }
}


