package no.bouvet.kpro.tagger;

import org.testng.annotations.*;
import no.bouvet.kpro.tagger.model.Part;
import no.bouvet.kpro.tagger.model.SimpleSong;
import no.bouvet.kpro.tagger.persistence.XStreamParser;

public class PlayStuffTest {

    SimpleSong simpleSong;

    @BeforeMethod
    private void beforeMethod() {
        XStreamParser parser = new XStreamParser();
        simpleSong = (SimpleSong) parser.load("/Volumes/McFeasty/Users/Stig/kpro/holden-nothing-93_returning_mix.dvl");
    }


    @Test
    public void testPlayingSomeStuff() throws Exception {
        Part part1 = new Part();
        part1.setSimpleSong(simpleSong);
        part1.setBpm(simpleSong.bpm);
        part1.setStartCue(0F);
        part1.setEndCue(16F);
        part1.setRow(simpleSong.rows.get(3));

        Part part2 = new Part();
        part2.setSimpleSong(simpleSong);
        part2.setBpm(simpleSong.bpm);
        part2.setStartCue(12F);
        part2.setEndCue(32F);
        part2.setRow(simpleSong.rows.get(6));

        Part part3 = new Part();
        part3.setSimpleSong(simpleSong);
        part3.setBpm(simpleSong.bpm);
        part3.setStartCue(32F);
        part3.setEndCue(62.5F);
        part3.setRow(simpleSong.rows.get(9));

        Part part4 = new Part();
        part4.setSimpleSong(simpleSong);
        part4.setBpm(simpleSong.bpm);
        part4.setStartCue(62F);
        part4.setEndCue(63.5F);
        part4.setRow(simpleSong.rows.get(10));

        Part part5 = new Part();
        part5.setSimpleSong(simpleSong);
        part5.setBpm(simpleSong.bpm);
        part5.setStartCue(63F);
        part5.setEndCue(64.5F);
        part5.setRow(simpleSong.rows.get(11));

        Part part6 = new Part();
        part6.setSimpleSong(simpleSong);
        part6.setBpm(simpleSong.bpm);
        part6.setStartCue(64F);
        part6.setEndCue(128F);
        part6.setRow(simpleSong.rows.get(12));

        PlayStuff playStuff = new PlayStuff();
        playStuff.bpm = 130F;
        playStuff.parts.add(part1);
        playStuff.parts.add(part2);
        playStuff.parts.add(part3);
        playStuff.parts.add(part4);
        playStuff.parts.add(part5);
        playStuff.parts.add(part6);
        playStuff.init();
        playStuff.play(60);
    }
}
