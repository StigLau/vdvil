package no.lau.vdvil;

import no.bouvet.kpro.tagger.PlayStuff;
import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.lau.tagger.model.*;
import no.lau.vdvil.cache.VdvilCache;
import no.lau.vdvil.cache.testresources.TestMp3s;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlayingAMix {

    URL holdenUrl = TestMp3s.returningDvl;
    URL psylteUrl = TestMp3s.psylteDvl;
    VdvilCache cache = VdvilHttpCache.create();

    @Test
    public void testMixing() throws FileNotFoundException, InterruptedException {
        SimpleSong nothing = new XStreamParser().load(cache.fetchAsStream(holdenUrl));
        SimpleSong psylteFlesk = new XStreamParser().load(cache.fetchAsStream(psylteUrl));
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


