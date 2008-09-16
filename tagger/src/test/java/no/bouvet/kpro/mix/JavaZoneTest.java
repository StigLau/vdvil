package no.bouvet.kpro.mix;

import no.bouvet.kpro.tagger.model.*;
import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.bouvet.kpro.tagger.PlayStuff;

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
        returning = (SimpleSong) parser.load("/Volumes/McFeasty/Users/Stig/kpro/holden-nothing-93_returning_mix.dvl");
        unfinished_sympathy = (SimpleSong) parser.load("/Volumes/McFeasty/Users/Stig/kpro/unfinished_sympathy.dvl");
        not_alone = (SimpleSong) parser.load("/Volumes/McFeasty/Users/Stig/kpro/olive-youre_not_alone.dvl");
        scares_me = (SimpleSong) parser.load("/Volumes/McFeasty/Users/Stig/kpro/christian_cambas-it_scares_me.dvl");

    }


    public void testPlayingSomeStuff() throws Exception {
        MasterSong composition = new MasterSong();

        composition.setMasterBpm(150F);
        composition.addPart(createPart(not_alone, 0F, 32F, not_alone.rows.get(0)));
        composition.addPart(createPart(scares_me, 16F, 48F, scares_me.rows.get(2)));
        composition.addPart(createPart(not_alone, 32F, 70F, not_alone.rows.get(1)));
        composition.addPart(createPart(scares_me, 48F, 64F, scares_me.rows.get(2)));
        composition.addPart(createPart(scares_me, 64F, 112F, scares_me.rows.get(4)));
        composition.addPart(createPart(returning, 96F, 140F, returning.rows.get(4)));
        composition.addPart(createPart(returning, 96F, 140F, returning.rows.get(4)));
        composition.addPart(createPart(returning, 128F, 174F, returning.rows.get(6)));
        composition.addPart(createPart(returning, 144F, 174.5F, returning.rows.get(9)));
        composition.addPart(createPart(returning, 174F, 175.5F, returning.rows.get(10)));
        composition.addPart(createPart(returning, 175F, 176.5F, returning.rows.get(11)));
        composition.addPart(createPart(returning, 176F, 240F, returning.rows.get(12)));
        composition.addPart(createPart(scares_me, 208F, 224F, scares_me.rows.get(12)));
        composition.addPart(createPart(scares_me, 224F, 252F, scares_me.rows.get(13)));

        PlayStuff player = new PlayStuff();
        player.setMasterSong(composition);
        player.init();
        player.play(0F);
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
