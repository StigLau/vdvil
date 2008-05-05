package no.bouvet.kpro.tagger;

import no.bouvet.kpro.tagger.model.*;
import no.bouvet.kpro.tagger.persistence.XStreamParser;

public class PlayingStuffGroovy {

    SimpleSong nothing;
    SimpleSong psylteFlesk;

    public static void main(String[] args) {
        PlayingStuffGroovy test = new PlayingStuffGroovy();
        test.beforeMethod();
        try {
            test.testPlayingSomeStuff();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void beforeMethod() {
        XStreamParser parser = new XStreamParser();
        nothing = (SimpleSong) parser.load("/Volumes/McFeasty/Users/Stig/kpro/holden-nothing-93_returning_mix.dvl");
        psylteFlesk = (SimpleSong) parser.load("/Volumes/McFeasty/Users/Stig/kpro/loaderror-psylteflesk.dvl");
    }


    public void testPlayingSomeStuff() throws Exception {
        MasterSong master = new MasterSong();

        Part part1 = createPart(0F, 20F, nothing.rows.get(3));
        Volume volume1 = new Volume(startValue:1F, endValue:0.8F);
        volume1.addAffectedPart(part1);
        master.addEffect(volume1);
        master.addPart(part1);

        Part part12 = createPart(19.99F, 32F, nothing.rows.get(3));
        part12.setBeginAtCue (16F)
        Volume volume12 = new Volume(startValue:0.8F, endValue:0.01F);
        volume12.addAffectedPart(part12);
        master.addEffect(volume12);
        master.addPart(part12);


        Part part2 = createPart(12F, 52F, nothing.rows.get(6))
        part2.setBeginAtCue (-8F)
        Volume vol2 = new Volume(startValue:0.5F, endValue:1F)
        vol2.addAffectedPart part2
        master.addEffect(vol2)
        master.addPart(part2)

        /*

        
        master.addPart(createPart(32F, 62.5F, nothing.rows.get(9)));

        Part part32 = createPart(32F, 62.5F, nothing.rows.get(9));
        master.addEffect(new Rate(1F, 0.9999F, 32F, 62.5F, part32));
        master.addPart(part2);

        master.addPart(createPart(62F, 63.5F, nothing.rows.get(10)));
        master.addPart(createPart(63F, 64.5F, nothing.rows.get(11)));
        master.addPart(createPart(64F, 128F, nothing.rows.get(12)));
        master.addPart(createPart(128F, 256F, nothing.rows.get(14)));
*/
        PlayStuff playStuff = new PlayStuff();
        playStuff.setMasterSong(master);
        playStuff.setBpm(130F);
        playStuff.init();
        playStuff.play(0F);
    }

    private Part createPart(Float start, Float end, Row row) {
        Part part = new Part();
        part.setSimpleSong(nothing);
        part.setBpm(nothing.bpm);
        part.setStartCue(start);
        part.setEndCue(end);
        part.setRow(row);
        return part;
    }
}

