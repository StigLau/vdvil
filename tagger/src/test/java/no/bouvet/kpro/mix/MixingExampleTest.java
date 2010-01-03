package no.bouvet.kpro.mix;

import no.bouvet.kpro.tagger.model.*;
import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.bouvet.kpro.tagger.PlayStuff;
import static no.bouvet.kpro.tagger.PartCreationUtil.createPart;

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
        space = parser.load("/Users/stiglau/kpro/space_manoeuvres-stage_one_original.dvl");

    }


    public void testPlayingSomeStuff() throws Exception {
        MasterSong composition = new MasterSong();

        composition.masterBpm = (135F);
        composition.parts.add(createPart(space, 0F, 96F, space.segments.get(0)));


        Float startMixinAt = 32F;
        composition.parts.add(createPart(returning, startMixinAt + -1F, startMixinAt + 16F, returning.segments.get(4)));

        startMixinAt += 64F;
        composition.parts.add(createPart(space, 80F, 96F, space.segments.get(1)));
        
        composition.parts.add(createPart(returning, startMixinAt + -8.05F, startMixinAt + -6.7F, returning.segments.get(7)));
        composition.parts.add(createPart(returning, startMixinAt + -6.05F, startMixinAt + -4.7F, returning.segments.get(7)));
        composition.parts.add(createPart(returning, startMixinAt + -4.05F, startMixinAt + -3.2F, returning.segments.get(7)));
        composition.parts.add(createPart(returning, startMixinAt + -3F, startMixinAt + -2.2F, returning.segments.get(7)));
        composition.parts.add(createPart(returning, startMixinAt + -2F, startMixinAt + -1.2F, returning.segments.get(7)));
        composition.parts.add(createPart(returning, startMixinAt + -1F, startMixinAt + -0.2F, returning.segments.get(7)));
        composition.parts.add(createPart(returning, startMixinAt + 0F, startMixinAt + 128F, returning.segments.get(7)));


        

        PlayStuff player = new PlayStuff();
        player.setMasterSong(composition);
        player.init();
        player.play(0F);
    }
}