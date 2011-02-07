package no.bouvet.kpro.tagger;

import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.AudioPart;
import no.lau.tagger.model.SimpleSong;
import org.codehaus.httpcache4j.cache.VdvilCacheStuff;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PlayingStuffDemo {

    SimpleSong returning;
    SimpleSong unfinished_sympathy;
    public static void main(String[] args) {
        PlayingStuffDemo test = new PlayingStuffDemo();
        try {
            test.beforeMethod();
            PlayStuff playStuff = new PlayStuff(new Composition(160F, test.parts()));
            playStuff.play(0F);
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }

    public void beforeMethod() throws FileNotFoundException {
        returning = new XStreamParser().load(VdvilCacheStuff.fetchAsStream("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"));
        unfinished_sympathy = new XStreamParser().load(VdvilCacheStuff.fetchAsStream("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/unfinished_sympathy.dvl"));
    }

    public List<AudioPart> parts(){
        List<AudioPart> parts = new ArrayList<AudioPart>();
        parts.add(new AudioPart(returning, 0F, 16F, returning.segments.get(3)));
        parts.add(new AudioPart(returning, 12F, 32F, returning.segments.get(6)));
        parts.add(new AudioPart(returning, 32F, 62.5F, returning.segments.get(9)));
        parts.add(new AudioPart(returning, 62F, 63.5F, returning.segments.get(10)));
        parts.add(new AudioPart(returning, 63F, 64.5F, returning.segments.get(11)));
        parts.add(new AudioPart(returning, 64F, 128F, returning.segments.get(12)));
        parts.add(new AudioPart(returning, 128F, 256F, returning.segments.get(14)));
        return parts;
    }
}
