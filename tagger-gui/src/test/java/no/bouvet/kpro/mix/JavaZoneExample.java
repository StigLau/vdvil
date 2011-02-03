package no.bouvet.kpro.mix;

import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.bouvet.kpro.tagger.PlayStuff;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.AudioPart;
import no.lau.tagger.model.SimpleSong;
import no.lau.vdvil.common.VdvilFileCache;
import org.codehaus.httpcache4j.cache.VdvilCacheStuff;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JavaZoneExample {
    static VdvilFileCache cache = new VdvilCacheStuff(new File("/tmp/vdvil"));

    SimpleSong returning;
    SimpleSong unfinished_sympathy;
    SimpleSong not_alone;
    SimpleSong scares_me;

    public static void main(String[] args) {
        JavaZoneExample test = new JavaZoneExample();
        test.beforeMethod();
        try {
            PlayStuff player = new PlayStuff(new Composition(150F, test.parts()), cache);
            player.play(0F);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void beforeMethod() {
        XStreamParser parser = new XStreamParser();
        returning = parser.load("/Users/stiglau/kpro/holden-nothing-93_returning_mix.dvl");
        unfinished_sympathy = parser.load("/Users/stiglau/kpro/unfinished_sympathy.dvl");
        not_alone = parser.load("/Users/stiglau/kpro/olive-youre_not_alone.dvl");
        scares_me = parser.load("/Users/stiglau/kpro/christian_cambas-it_scares_me.dvl");

    }


    public List<AudioPart> parts() throws Exception {
        List<AudioPart> parts = new ArrayList<AudioPart>();
        parts.add(new AudioPart(not_alone, 0F, 32F, not_alone.segments.get(0)));
        parts.add(new AudioPart(scares_me, 16F, 48F, scares_me.segments.get(2)));
        parts.add(new AudioPart(not_alone, 32F, 70F, not_alone.segments.get(1)));
        parts.add(new AudioPart(scares_me, 48F, 64F, scares_me.segments.get(2)));
        parts.add(new AudioPart(scares_me, 64F, 112F, scares_me.segments.get(4)));
        parts.add(new AudioPart(returning, 96F, 140F, returning.segments.get(4)));
        parts.add(new AudioPart(returning, 96F, 140F, returning.segments.get(4)));
        parts.add(new AudioPart(returning, 128F, 174F, returning.segments.get(6)));
        parts.add(new AudioPart(returning, 144F, 174.5F, returning.segments.get(9)));
        parts.add(new AudioPart(returning, 174F, 175.5F, returning.segments.get(10)));
        parts.add(new AudioPart(returning, 175F, 176.5F, returning.segments.get(11)));
        parts.add(new AudioPart(returning, 176F, 240F, returning.segments.get(12)));
        parts.add(new AudioPart(scares_me, 208F, 224F, scares_me.segments.get(12)));
        parts.add(new AudioPart(scares_me, 224F, 252F, scares_me.segments.get(13)));

        return parts;
    }
}
