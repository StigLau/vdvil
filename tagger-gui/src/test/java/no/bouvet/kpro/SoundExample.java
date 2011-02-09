package no.bouvet.kpro;

import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.bouvet.kpro.tagger.PlayStuff;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.AudioPart;
import no.lau.tagger.model.SimpleSong;
import org.codehaus.httpcache4j.cache.VdvilCacheStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SoundExample {
    public static void main(String[] args) throws Exception {
        Logger log = LoggerFactory.getLogger(SoundExample.class);
        PlayStuff player = new PlayStuff(new Composition(135F, SoundExample.parts()));
        try {
            player.play(0F);
            Thread.sleep(3000);
        } catch (Exception e) {
            log.error("Problem playing", e);
        }finally {
            player.stop();
            Thread.sleep(200);
        }
        System.exit(0);
    }
    public static List<AudioPart> parts() throws FileNotFoundException {
        SimpleSong returning = new XStreamParser().load(VdvilCacheStuff.fetchAsStream("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl"));
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