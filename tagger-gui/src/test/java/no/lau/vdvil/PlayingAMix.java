package no.lau.vdvil;

import com.thoughtworks.xstream.XStream;
import no.bouvet.kpro.tagger.PlayStuff;
import no.lau.tagger.model.*;
import org.codehaus.httpcache4j.cache.VdvilCacheStuff;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PlayingAMix {

    SimpleSong nothing;
    SimpleSong psylteFlesk;

    VdvilCacheStuff persistantCache = new VdvilCacheStuff(new File("/tmp/vdvil"));
    String holdenUrl = "http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl";
    String psylteUrl = "http://github.com/StigLau/vdvil/raw/master/tagger-gui/src/test/resources/loaderror-psylteflesk.dvl";

    public static void main(String[] args) {
        PlayingAMix test = new PlayingAMix();
        test.beforeMethod();
        try {
            test.testPlayingSomeStuff();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void beforeMethod() {
        InputStream holdenStream = persistantCache.fetchAsInputStream(holdenUrl);
        nothing = fetchMediaFile(createSimpleSongFromXML(holdenStream));
        InputStream psylteFleskStream = persistantCache.fetchAsInputStream(psylteUrl);
        psylteFlesk = fetchMediaFile(createSimpleSongFromXML(psylteFleskStream));
    }

    private SimpleSong createSimpleSongFromXML(InputStream inputStream) {
        XStream xstream = new XStream();
        xstream.alias("track", SimpleSong.class);
        xstream.alias("segment", Segment.class);
        return (SimpleSong) xstream.fromXML(inputStream);
    }

    private SimpleSong fetchMediaFile(SimpleSong simpleSong) {
        File file = persistantCache.fetchAsFile(simpleSong.mediaFile.fileName);
        return new SimpleSong(
                simpleSong.reference, 
                new MediaFile(
                        file.getAbsolutePath(),
                        simpleSong.mediaFile.checksum,
                        simpleSong.mediaFile.startingOffset),
                simpleSong.segments,
                simpleSong.bpm);
    }


    public void testPlayingSomeStuff() throws Exception {
        List<AudioPart> parts = new ArrayList<AudioPart>();
        System.out.println("playing mix");
        parts.add(new AudioPart(nothing, 0F, 20F, nothing.segments.get(3)));
        parts.add(new AudioPart(psylteFlesk, 8F, 16F, psylteFlesk.segments.get(4)));
        parts.add(new AudioPart(nothing, 12F, 52F, nothing.segments.get(6)));
        parts.add(new AudioPart(nothing, 19.99F, 32F, nothing.segments.get(3)));

        PlayStuff playStuff = new PlayStuff((new Composition(130F, parts)));
        playStuff.play(0F);
    }
}


