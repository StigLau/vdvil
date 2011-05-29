package no.lau.vdvil.mix;

import no.bouvet.kpro.tagger.PlayStuff;
import no.bouvet.kpro.tagger.persistence.XStreamParser;
import no.lau.tagger.model.AudioPart;
import no.lau.tagger.model.Composition;
import no.lau.tagger.model.SimpleSong;
import no.lau.vdvil.cache.VdvilCache;
import no.lau.vdvil.cache.testresources.TestMp3s;
import org.codehaus.httpcache4j.cache.VdvilHttpCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JavaZoneHttpCacheExample {
    static Logger log = LoggerFactory.getLogger(JavaZoneHttpCacheExample.class);
    VdvilCache cache = VdvilHttpCache.create();

    URL returningDvlUrl = TestMp3s.returningDvl;
    URL notAloneDvlUrl = TestMp3s.not_aloneDvl;
    URL scaresMeDvlUrl = TestMp3s.scares_meDvl;
    URL unfinishedSympathyDvlUrl = TestMp3s.unfinishedSympathyDvl;

    SimpleSong returning;
    SimpleSong unfinished_sympathy;
    SimpleSong not_alone;
    SimpleSong scares_me;

    public static void main(String[] args) {
        JavaZoneHttpCacheExample example = new JavaZoneHttpCacheExample();
        try {
            example.cacheFilesBeforePlayback();
            PlayStuff player = new PlayStuff(new Composition(150F, example.parts()));
            log.info("Starting JavaZoneHttpCacheExample playback");
            player.play(0);
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

    private SimpleSong fetchSimpleSongAndCacheDvlAndMp3(URL dvlUrl) throws FileNotFoundException {
        InputStream dvlStream = cache.fetchAsStream(dvlUrl);
        return new XStreamParser().load(dvlStream);
    }


    public List<AudioPart> parts() throws Exception {
        List<AudioPart> parts = new ArrayList<AudioPart>();
        parts.add(new AudioPart(not_alone, 0, 32, not_alone.segments.get(0)));
        parts.add(new AudioPart(scares_me, 16, 48, scares_me.segments.get(2)));
        parts.add(new AudioPart(not_alone, 32, 70, not_alone.segments.get(1)));
        parts.add(new AudioPart(scares_me, 48, 64, scares_me.segments.get(2)));
        parts.add(new AudioPart(scares_me, 64, 112, scares_me.segments.get(4)));
        parts.add(new AudioPart(returning, 96, 140, returning.segments.get(4)));
        parts.add(new AudioPart(returning, 96, 140, returning.segments.get(4)));
        parts.add(new AudioPart(returning, 128, 174, returning.segments.get(6)));
        parts.add(new AudioPart(returning, 144, 174, returning.segments.get(9)));
        parts.add(new AudioPart(returning, 174, 175, returning.segments.get(10)));
        parts.add(new AudioPart(returning, 175, 176, returning.segments.get(11)));
        parts.add(new AudioPart(returning, 176, 240, returning.segments.get(12)));
        parts.add(new AudioPart(scares_me, 208, 224, scares_me.segments.get(12)));
        parts.add(new AudioPart(scares_me, 224, 252, scares_me.segments.get(13)));

        return parts;
    }
}
