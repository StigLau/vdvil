package no.bouvet.kpro.mix;

import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.bouvet.kpro.tagger.PlayStuff;
import no.lau.tagger.model.*;
import org.codehaus.httpcache4j.cache.VdvilCacheStuff;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MixingExample {
    SimpleSong returning;
    SimpleSong unfinished_sympathy;
    SimpleSong not_alone;
    SimpleSong scares_me;
    SimpleSong space;

    public static void main(String[] args) throws FileNotFoundException {
        MixingExample test = new MixingExample();
        test.beforeMethod();
        try {
            PlayStuff player = new PlayStuff(new Composition(135F, test.parts()));

            player.play(0F);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO Checksums don't match
    public void beforeMethod() throws FileNotFoundException {
        returning = new XStreamParser().load(VdvilCacheStuff.fetchAsStream("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"));
        unfinished_sympathy = new XStreamParser().load(VdvilCacheStuff.fetchAsStream("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/unfinished_sympathy.dvl"));
        not_alone = new XStreamParser().load(VdvilCacheStuff.fetchAsStream("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/olive-youre_not_alone.dvl"));
        scares_me = new XStreamParser().load(VdvilCacheStuff.fetchAsStream("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/christian_cambas-it_scares_me.dvl"));
        space = new XStreamParser().load(VdvilCacheStuff.fetchAsStream("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/space_manoeuvres-stage_one_original.dvl"));
    }


    public List<AudioPart> parts() throws Exception {
        List<AudioPart> parts = new ArrayList<AudioPart>();
        parts.add(new AudioPart(space, 0F, 96F, space.segments.get(0)));

        Float startMixinAt = 32F;
        List<Segment> returningSegments = returning.segments;
        parts.add(new AudioPart(returning, startMixinAt + -1F, startMixinAt + 16F, returningSegments.get(4)));

        startMixinAt += 64F;
        parts.add(new AudioPart(space, 80F, 96F, space.segments.get(1)));
        
        parts.add(new AudioPart(returning, startMixinAt + -8.05F, startMixinAt + -6.7F, returningSegments.get(7)));
        parts.add(new AudioPart(returning, startMixinAt + -6.05F, startMixinAt + -4.7F, returningSegments.get(7)));
        parts.add(new AudioPart(returning, startMixinAt + -4.05F, startMixinAt + -3.2F, returningSegments.get(7)));
        parts.add(new AudioPart(returning, startMixinAt + -3F, startMixinAt + -2.2F, returningSegments.get(7)));
        parts.add(new AudioPart(returning, startMixinAt + -2F, startMixinAt + -1.2F, returningSegments.get(7)));
        parts.add(new AudioPart(returning, startMixinAt + -1F, startMixinAt + -0.2F, returningSegments.get(7)));
        parts.add(new AudioPart(returning, startMixinAt + 0F, startMixinAt + 128F, returningSegments.get(7)));
        return parts;
    }
}