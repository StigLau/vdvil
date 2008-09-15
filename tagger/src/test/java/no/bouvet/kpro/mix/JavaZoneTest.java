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
        MasterSong master = new MasterSong();
        /*
        master.setMasterBpm(130F);
        master.addPart(createPart(unfinished_sympathy, 0F, 16F, unfinished_sympathy.rows.get(3)));
        master.addPart(createPart(unfinished_sympathy, 16F, 32F, unfinished_sympathy.rows.get(6)));
        master.addPart(createPart(returning, 32F, 48F, returning.rows.get(6)));
        master.addPart(createPart(unfinished_sympathy, 32F, 64F, unfinished_sympathy.rows.get(8)));
*/
        master.setMasterBpm(160F);
        master.addPart(createPart(not_alone, 0F, 32F, not_alone.rows.get(0)));
        master.addPart(createPart(scares_me, 16F, 48F, scares_me.rows.get(2)));
        master.addPart(createPart(not_alone, 32F, 70F, not_alone.rows.get(1)));
        master.addPart(createPart(scares_me, 48F, 64F, scares_me.rows.get(2)));
        master.addPart(createPart(scares_me, 64F, 112F, scares_me.rows.get(4)));

        master.addPart(createPart(returning, 96F, 140F, returning.rows.get(4)));
        master.addPart(createPart(returning, 96F, 140F, returning.rows.get(4)));

        master.addPart(createPart(returning, 128F, 174F, returning.rows.get(6)));
        master.addPart(createPart(returning, 144F, 174.5F, returning.rows.get(9)));
        master.addPart(createPart(returning, 174F, 175.5F, returning.rows.get(10)));
        master.addPart(createPart(returning, 175F, 176.5F, returning.rows.get(11)));
        master.addPart(createPart(returning, 176F, 240F, returning.rows.get(12)));

        master.addPart(createPart(scares_me, 208F, 224F, scares_me.rows.get(12)));
        master.addPart(createPart(scares_me, 224F, 252F, scares_me.rows.get(13)));
        //master.addPart(createPart(not_alone, 240F, 256F, not_alone.rows.get(7)));
        //master.addPart(createPart(not_alone, 271F, 296F, not_alone.rows.get(9)));

        
        //master.addPart(createPart(unfinished_sympathy, 240F, 300F, unfinished_sympathy.rows.get(7)));




        //master.addPart(createPart(scares_me, 32F, 64F, scares_me.rows.get(2)));
        //master.addPart(createPart(scares_me, 32F, 64F, scares_me.rows.get(4)));
        //master.addPart(createPart(not_alone, 64F, 128F, not_alone.rows.get(3)));
        //master.addPart(createPart(scares_me, 64F, 128F, scares_me.rows.get(4)));



        /*
        master.addPart(createPart(returning, 16F, 32F, returning.rows.get(6)));
        master.addPart(createPart(returning, 32F, 62.5F, returning.rows.get(9)));
        master.addPart(createPart(returning, 62F, 63.5F, returning.rows.get(10)));
        master.addPart(createPart(returning, 63F, 64.5F, returning.rows.get(11)));
        master.addPart(createPart(returning, 64F, 128F, returning.rows.get(12)));
        */
        //master.addPart(createPart(returning, 128F, 256F, returning.rows.get(14)));


        PlayStuff playStuff = new PlayStuff();
        playStuff.setMasterSong(master);
        playStuff.init();
        playStuff.play(0F);
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
