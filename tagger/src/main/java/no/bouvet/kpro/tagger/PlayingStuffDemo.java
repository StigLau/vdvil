package no.bouvet.kpro.tagger;

import no.bouvet.kpro.tagger.model.*;
import no.bouvet.kpro.tagger.persistence.XStreamParser;

public class PlayingStuffDemo {

    SimpleSong returning;
    SimpleSong unfinished_sympathy;

    public static void main(String[] args) {
        PlayingStuffDemo test = new PlayingStuffDemo();
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
        master.addPart(createPart(returning, 0F, 16F, returning.rows.get(3)));
        master.addPart(createPart(returning, 12F, 32F, returning.rows.get(6)));
        master.addPart(createPart(returning, 32F, 62.5F, returning.rows.get(9)));
        master.addPart(createPart(returning, 62F, 63.5F, returning.rows.get(10)));
        master.addPart(createPart(returning, 63F, 64.5F, returning.rows.get(11)));
        master.addPart(createPart(returning, 64F, 128F, returning.rows.get(12)));
        master.addPart(createPart(returning, 128F, 256F, returning.rows.get(14)));

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
