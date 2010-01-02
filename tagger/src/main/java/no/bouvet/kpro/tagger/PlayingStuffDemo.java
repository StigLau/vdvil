package no.bouvet.kpro.tagger;

import no.bouvet.kpro.tagger.model.*;
import no.bouvet.kpro.tagger.persistence.XStreamParser;
import static no.bouvet.kpro.tagger.PartCreationUtil.createPart;

public class PlayingStuffDemo {

    SimpleSong returning;
    SimpleSong unfinished_sympathy;

    public static void main(String[] args) {
        PlayingStuffDemo test = new PlayingStuffDemo();
        test.beforeMethod();
        PlayStuff playStuff = test.createPlayStuff();
        try {
            playStuff.init();
            playStuff.play(0F);
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }

    public void beforeMethod() {
        XStreamParser parser = new XStreamParser();
        returning = (SimpleSong) parser.load("/Users/stiglau/kpro/holden-nothing-93_returning_mix.dvl_oldFormat");
        unfinished_sympathy = (SimpleSong) parser.load("/Users/stiglau/kpro/unfinished_sympathy.dvl_backup");

    }


    public PlayStuff createPlayStuff(){
        MasterSong master = new MasterSong();
        /*
        master.setMasterBpm(130F);
        master.addPart(createPart(unfinished_sympathy, 0F, 16F, unfinished_sympathy.rows.get(3)));
        master.addPart(createPart(unfinished_sympathy, 16F, 32F, unfinished_sympathy.rows.get(6)));
        master.addPart(createPart(returning, 32F, 48F, returning.rows.get(6)));
        master.addPart(createPart(unfinished_sympathy, 32F, 64F, unfinished_sympathy.rows.get(8)));
*/
        master.masterBpm = 160F;
        master.parts.add(createPart(returning, 0F, 16F, returning.rows.get(3)));
        master.parts.add(createPart(returning, 12F, 32F, returning.rows.get(6)));
        master.parts.add(createPart(returning, 32F, 62.5F, returning.rows.get(9)));
        master.parts.add(createPart(returning, 62F, 63.5F, returning.rows.get(10)));
        master.parts.add(createPart(returning, 63F, 64.5F, returning.rows.get(11)));
        master.parts.add(createPart(returning, 64F, 128F, returning.rows.get(12)));
        master.parts.add(createPart(returning, 128F, 256F, returning.rows.get(14)));

        PlayStuff playStuff = new PlayStuff();                                                      
        playStuff.setMasterSong(master);
        return playStuff;
    }
}
