package no.bouvet.kpro.tagger;


import no.bouvet.kpro.tagger.persistence.XStreamParser
import no.lau.tagger.model.Part
import no.lau.tagger.model.SimpleSong
import no.lau.tagger.model.Composition;

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
        nothing = parser.load("/Users/stiglau/kpro/holden-nothing-93_returning_mix.dvl");
        psylteFlesk = parser.load("/Users/stiglau/kpro/loaderror-psylteflesk.dvl");
    }


    public void testPlayingSomeStuff() throws Exception {
      def parts = [
          new Part(nothing, 0F, 20F, nothing.segments.get(3)),
          new Part(nothing, 19.99F, 32F, nothing.segments.get(3)),
          new Part(psylteFlesk, 8F, 16F, psylteFlesk.segments.get(4)),
          new Part(nothing, 12F, 52F, nothing.segments.get(6))
      ]
        PlayStuff playStuff = new PlayStuff();
        playStuff.setMasterSong(new Composition(130F, parts));
        playStuff.init();
        playStuff.play(0F);
    }
}

