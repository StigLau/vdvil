package no.bouvet.kpro.tagger;

import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.Part;
import no.lau.tagger.model.SimpleSong;

import java.util.ArrayList;
import java.util.List;

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
        returning = parser.load("/Users/stiglau/kpro/holden-nothing-93_returning_mix.dvl");
        unfinished_sympathy = parser.load("/Users/stiglau/kpro/unfinished_sympathy.dvl");

    }


    public PlayStuff createPlayStuff(){

        /*
        master.setMasterBpm(130F);
        master.addPart(createPart(unfinished_sympathy, 0F, 16F, unfinished_sympathy.segments.get(3)));
        master.addPart(createPart(unfinished_sympathy, 16F, 32F, unfinished_sympathy.segments.get(6)));
        master.addPart(createPart(returning, 32F, 48F, returning.segments.get(6)));
        master.addPart(createPart(unfinished_sympathy, 32F, 64F, unfinished_sympathy.segments.get(8)));
*/
        List<Part> parts = new ArrayList<Part>();
        parts.add(new Part(returning, 0F, 16F, returning.segments.get(3)));
        parts.add(new Part(returning, 12F, 32F, returning.segments.get(6)));
        parts.add(new Part(returning, 32F, 62.5F, returning.segments.get(9)));
        parts.add(new Part(returning, 62F, 63.5F, returning.segments.get(10)));
        parts.add(new Part(returning, 63F, 64.5F, returning.segments.get(11)));
        parts.add(new Part(returning, 64F, 128F, returning.segments.get(12)));
        parts.add(new Part(returning, 128F, 256F, returning.segments.get(14)));

        PlayStuff playStuff = new PlayStuff();
        playStuff.setMasterSong(new Composition(160F, parts));
        return playStuff;
    }
}
