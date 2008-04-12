package no.bouvet.kpro.tagger;

import org.testng.annotations.Test;
import no.bouvet.kpro.tagger.model.Row;
import no.bouvet.kpro.tagger.model.SimpleSong;
import no.bouvet.kpro.tagger.persistence.SimpleSongParser;

public class SimpleSongParserTest {

    @Test
    public void testSavingSimpleSong() {
        SimpleSongParser simpleSongParser = new SimpleSongParser();
        simpleSongParser.save(coronaTest(), "/corona.dvl");
    }
    
    @Test
    public void testSavingAndLoadingSimpleSong() {
        SimpleSongParser simpleSongParser = new SimpleSongParser();
        simpleSongParser.save(coronaTest(),"/corona.dvl");

        assert (coronaTest().fileName.equals("/Volumes/McFeasty/Users/Stig/jobb/utvikling/bouvet/playground/stig.lau/kpro2007/renderer.audio/src/test/resources/Corona_-_Baby_Baby.mp3"));
    }

    public static SimpleSong coronaTest() {
        SimpleSong corona = new SimpleSong();

        corona.fileName = "/Volumes/McFeasty/Users/Stig/jobb/utvikling/bouvet/playground/stig.lau/kpro2007/renderer.audio/src/test/resources/Corona_-_Baby_Baby.mp3";
        corona.startingOffset = 44100 * 0.445f;
        corona.bpm = 132.98f;
        corona.rows.add(new Row(0F, 16F,  "Baby, why can't we just stay together"));
        corona.rows.add(new Row(16F, 32F,  "Baby, why can't we just stay together"));
        corona.rows.add(new Row(32F, 64F,  "Intro"));
        corona.rows.add(new Row(64F, 96F,  "Riff 1. time"));
        corona.rows.add(new Row(96F, 128F,  "1. Refrain  I want to roll inside your soul,"));
        corona.rows.add(new Row(128F, 128 + 32F,  "2. Verse - Caught you down by suprise"));
        corona.rows.add(new Row(128 + 32F, 128 + 64F,  "Baby baby, why can't we just stay together"));
        corona.rows.add(new Row(128 + 64F, 128 + 96F, "riff 2. time"));
        corona.rows.add(new Row(128 + 96F, 256F,  "Deep inside I know you need it"));
        corona.rows.add(new Row(256F, 256 + 32F, "Caught you down by suprise"));
        /*      0 // Baby baby
                16, //Baby baby, Why can't we just say forever
                32, //Intro
                64, //Riff 1. time
                96, //1. Refrain  I want to roll inside your soul,
                128, //2. Verse - Caught you down by suprise
                128 + 32, //Baby baby, why can't we just stay together
                128 + 64, //riff 2. time
                128 + 96, // Deep inside I know you need it
                256,         //Caught you down by suprise
                256 + 32, // Baby baby
                256 + 64,  // Baby baby
                256 + 96,  //Medley - Wheey
                384,
                384 + 32, // Riff 3. time
                384 + 64, //Baby Baby
                384 + 96  //Baby Baby, Why can't we just say forever  - End
*/
        return corona;
    }

}
