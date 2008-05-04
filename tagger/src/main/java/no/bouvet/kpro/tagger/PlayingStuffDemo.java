package no.bouvet.kpro.tagger;

import no.bouvet.kpro.tagger.model.*;
import no.bouvet.kpro.tagger.persistence.XStreamParser;

public class PlayingStuffDemo {

    SimpleSong simpleSong;

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
        simpleSong = (SimpleSong) parser.load("/Volumes/McFeasty/Users/Stig/kpro/holden-nothing-93_returning_mix.dvl");
    }


    public void testPlayingSomeStuff() throws Exception {
        MasterSong master = new MasterSong();

        Part part1 = createPart(0F, 16F, simpleSong.rows.get(3));
        Volume volume1 = new Volume(0.2F, 0.5F, 12F, 16F);
        volume1.addAffectedPart(part1);
        master.addEffect(volume1);
        master.addPart(part1);


        Part part2 = createPart(12F, 32F, simpleSong.rows.get(6));
        master.addEffect(new Volume(0.2F, 0.5F, 12F, 32F, part2));
        master.addPart(part2);

        master.addPart(createPart(32F, 62.5F, simpleSong.rows.get(9)));

        Part part32 = createPart(32F, 62.5F, simpleSong.rows.get(9));
        master.addEffect(new Rate(1F, 0.9999F, 32F, 62.5F, part32));
        master.addPart(part2);

        master.addPart(createPart(62F, 63.5F, simpleSong.rows.get(10)));
        master.addPart(createPart(63F, 64.5F, simpleSong.rows.get(11)));
        master.addPart(createPart(64F, 128F, simpleSong.rows.get(12)));
        master.addPart(createPart(128F, 256F, simpleSong.rows.get(14)));

        PlayStuff playStuff = new PlayStuff();
        playStuff.setMasterSong(master);
        playStuff.setBpm(130F);
        playStuff.init();
        playStuff.play(25F);
    }

    private Part createPart(Float start, Float end, Row row) {
        Part part = new Part();
        part.setSimpleSong(simpleSong);
        part.setBpm(simpleSong.bpm);
        part.setStartCue(start);
        part.setEndCue(end);
        part.setRow(row);
        return part;
    }
}
