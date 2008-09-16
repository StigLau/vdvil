package no.bouvet.kpro.mix;

import no.bouvet.kpro.tagger.model.*;
import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.bouvet.kpro.tagger.PlayStuff;

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
        returning = (SimpleSong) parser.load("/Volumes/McFeasty/Users/Stig/kpro/holden-nothing-93_returning_mix.dvl");
        unfinished_sympathy = (SimpleSong) parser.load("/Volumes/McFeasty/Users/Stig/kpro/unfinished_sympathy.dvl");
        not_alone = (SimpleSong) parser.load("/Volumes/McFeasty/Users/Stig/kpro/olive-youre_not_alone.dvl");
        scares_me = (SimpleSong) parser.load("/Volumes/McFeasty/Users/Stig/kpro/christian_cambas-it_scares_me.dvl");
        space = (SimpleSong) parser.load("/Volumes/McFeasty/Users/Stig/kpro/space_manoeuvres-stage_one_original.dvl");

    }


    public void testPlayingSomeStuff() throws Exception {
        MasterSong composition = new MasterSong();

        composition.setMasterBpm(135F);
        composition.addPart(createPart(space, 0F, 96F, space.rows.get(0)));


        Float startMixinAt = 32F;
        composition.addPart(createPart(space, startMixinAt + 0F, startMixinAt + 16F, space.rows.get(4)));

        startMixinAt += 64F;

        //composition.addPart(createPart(space, startMixinAt + -17F, startMixinAt + -12.9F, space.rows.get(5)));
        composition.addPart(createPart(returning, startMixinAt + -6F, startMixinAt + -5F, returning.rows.get(7)));
        composition.addPart(createPart(returning, startMixinAt + -4F, startMixinAt + -3F, returning.rows.get(7)));
        composition.addPart(createPart(returning, startMixinAt + -2F, startMixinAt + -1.2F, returning.rows.get(7)));
        composition.addPart(createPart(returning, startMixinAt + -1F, startMixinAt + -0.2F, returning.rows.get(7)));
        composition.addPart(createPart(returning, startMixinAt + 0F, startMixinAt + 64F, returning.rows.get(7)));

        composition.addPart(createPart(space, 96F, 160F, space.rows.get(1)));
        

        PlayStuff player = new PlayStuff();
        player.setMasterSong(composition);
        player.init();
        player.play(64F);
    }

    private Part createPart(SimpleSong ss, Float start, Float end, Row row) {
        Part part = new Part();
        part.setSimpleSong(ss);
        part.setBpm(ss.bpm);
        part.setStartCue(start);
        part.setEndCue(end);
        part.setRow(row);
        return part;
    }
}