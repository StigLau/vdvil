package no.lau.vdvil.mix;

import no.bouvet.kpro.tagger.PlayStuff;
import no.lau.tagger.model.AudioPart;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.SimpleSong;
import no.lau.vdvil.cache.VdvilCacheHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class JavaZoneHttpCacheExample {

    SimpleSong returning;
    SimpleSong unfinished_sympathy;
    SimpleSong not_alone;
    SimpleSong scares_me;

    final static Logger log = LoggerFactory.getLogger(VdvilCacheHandler.class);

    final static String returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl";
    final String returningDvlChecksum = "2f0bd28098bce29f555c713cc03ab625";
    final static String notAloneDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/olive-youre_not_alone.dvl";
    final String notAloneDvlChecksum = "24c8ee0a44711565a66f699d8f6e1b3d";
    final static String scaresMeDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/christian_cambas-it_scares_me.dvl";
    final String scaresMeDvlChecksum = "5c5164cc3c1621b541cc6f856b4bcd08";
    final static String unfinishedSympathyDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/unfinished_sympathy.dvl";
    final String unfinishedSympathyDvlChecksum = "43bc31c17b47f305ae4ef3e370d0d703";

    public static void main(String[] args) {
        JavaZoneHttpCacheExample test = new JavaZoneHttpCacheExample();
        test.beforeMethod();
        try {
            PlayStuff player = new PlayStuff(new Composition(150F, test.parts()));
            log.info("Starting JavaZoneHttpCacheExample playback");
            player.play(0F);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void beforeMethod() {
        try {
            VdvilCacheHandler cacheHandler = new VdvilCacheHandler();
            returning = cacheHandler.fetchSimpleSongAndCacheDvlAndMp3(returningDvlUrl, returningDvlChecksum);
            not_alone = cacheHandler.fetchSimpleSongAndCacheDvlAndMp3(notAloneDvlUrl, notAloneDvlChecksum);
            scares_me = cacheHandler.fetchSimpleSongAndCacheDvlAndMp3(scaresMeDvlUrl, scaresMeDvlChecksum);
            unfinished_sympathy = cacheHandler.fetchSimpleSongAndCacheDvlAndMp3(unfinishedSympathyDvlUrl, unfinishedSympathyDvlChecksum);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not download file ", e);
        }
    }


    public List<AudioPart> parts() throws Exception {
        List<AudioPart> parts = new ArrayList<AudioPart>();
        parts.add(new AudioPart(not_alone, 0F, 32F, not_alone.segments.get(0)));
        parts.add(new AudioPart(scares_me, 16F, 48F, scares_me.segments.get(2)));
        parts.add(new AudioPart(not_alone, 32F, 70F, not_alone.segments.get(1)));
        parts.add(new AudioPart(scares_me, 48F, 64F, scares_me.segments.get(2)));
        parts.add(new AudioPart(scares_me, 64F, 112F, scares_me.segments.get(4)));
        parts.add(new AudioPart(returning, 96F, 140F, returning.segments.get(4)));
        parts.add(new AudioPart(returning, 96F, 140F, returning.segments.get(4)));
        parts.add(new AudioPart(returning, 128F, 174F, returning.segments.get(6)));
        parts.add(new AudioPart(returning, 144F, 174.5F, returning.segments.get(9)));
        parts.add(new AudioPart(returning, 174F, 175.5F, returning.segments.get(10)));
        parts.add(new AudioPart(returning, 175F, 176.5F, returning.segments.get(11)));
        parts.add(new AudioPart(returning, 176F, 240F, returning.segments.get(12)));
        parts.add(new AudioPart(scares_me, 208F, 224F, scares_me.segments.get(12)));
        parts.add(new AudioPart(scares_me, 224F, 252F, scares_me.segments.get(13)));

        return parts;
    }
}
