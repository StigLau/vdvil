package no.lau.vdvil.mix;

import no.bouvet.kpro.tagger.PlayStuff;
import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.lau.tagger.model.AudioPart;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.SimpleSong;
import no.lau.vdvil.common.VdvilFileCache;
import org.codehaus.httpcache4j.cache.VdvilCacheStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JavaZoneHttpCacheExample {
    static VdvilFileCache cache = new VdvilCacheStuff(new File("/tmp/vdvil"));

    final static Logger log = LoggerFactory.getLogger(JavaZoneHttpCacheExample.class);

    final static String returningDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl";
    final static String notAloneDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/olive-youre_not_alone.dvl";
    final static String scaresMeDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/christian_cambas-it_scares_me.dvl";
    final static String unfinishedSympathyDvlUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/unfinished_sympathy.dvl";

    SimpleSong returning;
    SimpleSong unfinished_sympathy;
    SimpleSong not_alone;
    SimpleSong scares_me;

    public static void main(String[] args) {
        JavaZoneHttpCacheExample example = new JavaZoneHttpCacheExample();
        try {
            example.cacheFilesBeforePlayback();
            PlayStuff player = new PlayStuff(new Composition(150F, example.parts()), cache);
            log.info("Starting JavaZoneHttpCacheExample playback");
            player.play(0F);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cacheFilesBeforePlayback() throws FileNotFoundException {
        returning = fetchSimpleSongAndCacheDvlAndMp3(returningDvlUrl);
        not_alone = fetchSimpleSongAndCacheDvlAndMp3(notAloneDvlUrl);
        scares_me = fetchSimpleSongAndCacheDvlAndMp3(scaresMeDvlUrl);
        unfinished_sympathy = fetchSimpleSongAndCacheDvlAndMp3(unfinishedSympathyDvlUrl);

    }

    private SimpleSong fetchSimpleSongAndCacheDvlAndMp3(String dvlUrl) throws FileNotFoundException {
        InputStream dvlStream = cache.fetchAsStream(dvlUrl);
        return new XStreamParser().load(dvlStream);
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
