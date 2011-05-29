package no.bouvet.kpro.tagger;

import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.AudioPart;
import no.lau.tagger.model.SimpleSong;
import no.lau.vdvil.cache.VdvilCache;
import no.lau.vdvil.cache.testresources.TestMp3s;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PlayingStuffDemo {
    VdvilCache cache = VdvilHttpCache.create();

    SimpleSong returning;
    SimpleSong unfinished_sympathy;
    public static void main(String[] args) {
        PlayingStuffDemo test = new PlayingStuffDemo();
        try {
            test.beforeMethod();
            PlayStuff playStuff = new PlayStuff(new Composition(160F, test.parts()));
            playStuff.play(0);
        } catch (Exception e) {
            e.printStackTrace();  
        }
    }

    public void beforeMethod() throws FileNotFoundException {
        returning = new XStreamParser().load(cache.fetchAsStream(TestMp3s.returningDvl));
        unfinished_sympathy = new XStreamParser().load(cache.fetchAsStream(TestMp3s.unfinishedSympathyDvl));
    }

    public List<AudioPart> parts(){
        List<AudioPart> parts = new ArrayList<AudioPart>();
        parts.add(new AudioPart(returning, 0, 16, returning.segments.get(3)));
        parts.add(new AudioPart(returning, 12, 32, returning.segments.get(6)));
        parts.add(new AudioPart(returning, 32, 62, returning.segments.get(9)));
        parts.add(new AudioPart(returning, 62, 63, returning.segments.get(10)));
        parts.add(new AudioPart(returning, 63, 64, returning.segments.get(11)));
        parts.add(new AudioPart(returning, 64, 128, returning.segments.get(12)));
        parts.add(new AudioPart(returning, 128, 256, returning.segments.get(14)));
        return parts;
    }
}
