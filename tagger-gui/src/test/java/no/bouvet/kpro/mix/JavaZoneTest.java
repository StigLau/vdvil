package no.bouvet.kpro.mix;

import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.bouvet.kpro.tagger.PlayStuff;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.Part;
import no.lau.tagger.model.SimpleSong;

import java.util.ArrayList;
import java.util.List;

public class JavaZoneTest {

    SimpleSong returning;
    SimpleSong unfinished_sympathy;
    SimpleSong not_alone;
    SimpleSong scares_me;

    public static void main(String[] args) {
        JavaZoneTest test = new JavaZoneTest();
        test.beforeMethod();
        try {
            test.testPlayingSomeStuff();
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


    public void testPlayingSomeStuff() throws Exception {
        List<Part> parts = new ArrayList<Part>();
        parts.add(new Part(not_alone, 0F, 32F, not_alone.segments.get(0)));
        parts.add(new Part(scares_me, 16F, 48F, scares_me.segments.get(2)));
        parts.add(new Part(not_alone, 32F, 70F, not_alone.segments.get(1)));
        parts.add(new Part(scares_me, 48F, 64F, scares_me.segments.get(2)));
        parts.add(new Part(scares_me, 64F, 112F, scares_me.segments.get(4)));
        parts.add(new Part(returning, 96F, 140F, returning.segments.get(4)));
        parts.add(new Part(returning, 96F, 140F, returning.segments.get(4)));
        parts.add(new Part(returning, 128F, 174F, returning.segments.get(6)));
        parts.add(new Part(returning, 144F, 174.5F, returning.segments.get(9)));
        parts.add(new Part(returning, 174F, 175.5F, returning.segments.get(10)));
        parts.add(new Part(returning, 175F, 176.5F, returning.segments.get(11)));
        parts.add(new Part(returning, 176F, 240F, returning.segments.get(12)));
        parts.add(new Part(scares_me, 208F, 224F, scares_me.segments.get(12)));
        parts.add(new Part(scares_me, 224F, 252F, scares_me.segments.get(13)));

        PlayStuff player = new PlayStuff();
        player.setMasterSong(new Composition(150F, parts));
        player.init();
        player.play(0F);
    }
}
