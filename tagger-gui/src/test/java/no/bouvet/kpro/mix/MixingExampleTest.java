package no.bouvet.kpro.mix;

import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.bouvet.kpro.tagger.PlayStuff;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.Part;
import no.lau.tagger.model.Segment;
import no.lau.tagger.model.SimpleSong;

import java.util.ArrayList;
import java.util.List;

public class MixingExampleTest {

    SimpleSong returning;
    SimpleSong unfinished_sympathy;
    SimpleSong not_alone;
    SimpleSong scares_me;
    SimpleSong space;

    public static void main(String[] args) {
        MixingExampleTest test = new MixingExampleTest();
        test.beforeMethod();
        try {
            PlayStuff player = new PlayStuff(new Composition(135F, test.parts()));

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
        space = parser.load("/Users/stiglau/kpro/space_manoeuvres-stage_one_original.dvl");
    }


    public List<Part> parts() throws Exception {
        List<Part> parts = new ArrayList<Part>();
        parts.add(new Part(space, 0F, 96F, space.segments.get(0)));

        Float startMixinAt = 32F;
        List<Segment> returningSegments = returning.segments;
        parts.add(new Part(returning, startMixinAt + -1F, startMixinAt + 16F, returningSegments.get(4)));

        startMixinAt += 64F;
        parts.add(new Part(space, 80F, 96F, space.segments.get(1)));
        
        parts.add(new Part(returning, startMixinAt + -8.05F, startMixinAt + -6.7F, returningSegments.get(7)));
        parts.add(new Part(returning, startMixinAt + -6.05F, startMixinAt + -4.7F, returningSegments.get(7)));
        parts.add(new Part(returning, startMixinAt + -4.05F, startMixinAt + -3.2F, returningSegments.get(7)));
        parts.add(new Part(returning, startMixinAt + -3F, startMixinAt + -2.2F, returningSegments.get(7)));
        parts.add(new Part(returning, startMixinAt + -2F, startMixinAt + -1.2F, returningSegments.get(7)));
        parts.add(new Part(returning, startMixinAt + -1F, startMixinAt + -0.2F, returningSegments.get(7)));
        parts.add(new Part(returning, startMixinAt + 0F, startMixinAt + 128F, returningSegments.get(7)));
        return parts;
    }
}