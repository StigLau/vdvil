package no.bouvet.kpro.mix;

import no.bouvet.kpro.tagger.model.*;
import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.bouvet.kpro.tagger.PlayStuff;
import static no.bouvet.kpro.tagger.PartCreationUtil.createPart;

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
        MasterSong composition = new MasterSong();

        composition.masterBpm = 150F;
        composition.parts.add(createPart(not_alone, 0F, 32F, not_alone.segments.get(0)));
        composition.parts.add(createPart(scares_me, 16F, 48F, scares_me.segments.get(2)));
        composition.parts.add(createPart(not_alone, 32F, 70F, not_alone.segments.get(1)));
        composition.parts.add(createPart(scares_me, 48F, 64F, scares_me.segments.get(2)));
        composition.parts.add(createPart(scares_me, 64F, 112F, scares_me.segments.get(4)));
        composition.parts.add(createPart(returning, 96F, 140F, returning.segments.get(4)));
        composition.parts.add(createPart(returning, 96F, 140F, returning.segments.get(4)));
        composition.parts.add(createPart(returning, 128F, 174F, returning.segments.get(6)));
        composition.parts.add(createPart(returning, 144F, 174.5F, returning.segments.get(9)));
        composition.parts.add(createPart(returning, 174F, 175.5F, returning.segments.get(10)));
        composition.parts.add(createPart(returning, 175F, 176.5F, returning.segments.get(11)));
        composition.parts.add(createPart(returning, 176F, 240F, returning.segments.get(12)));
        composition.parts.add(createPart(scares_me, 208F, 224F, scares_me.segments.get(12)));
        composition.parts.add(createPart(scares_me, 224F, 252F, scares_me.segments.get(13)));

        PlayStuff player = new PlayStuff();
        player.setMasterSong(composition);
        player.init();
        player.play(0F);
    }
}
