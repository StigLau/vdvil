package no.lau.vdvil.cache.testresources;

import java.net.MalformedURLException;
import java.net.URL;

public class TestMp3s {
    public static final URL returningmp3 =          createURL("http://kpro09.googlecode.com/svn/test-files/holden-nothing-93_returning_mix.mp3");
    public static final URL coronamp3 =             createURL("https://github.com/StigLau/vdvil/blob/master/audio-renderer/src/test/resources/Corona_-_Baby_Baby.mp3");
    public static final URL psylteDvl =             createURL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/loaderror-psylteflesk.dvl");
    public static final URL returningDvl =          createURL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/holden-nothing-93_returning_mix.dvl");
    public static final URL unfinishedSympathyDvl = createURL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/unfinished_sympathy.dvl");
    public static final URL not_aloneDvl =          createURL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/olive-youre_not_alone.dvl");
    public static final URL scares_meDvl =          createURL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/christian_cambas-it_scares_me.dvl");
    public static final URL spaceDvl =              createURL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/dvl/space_manoeuvres-stage_one_original.dvl");

    public static final URL javaZoneComposition = createURL("http://kpro09.googlecode.com/svn/trunk/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml");
    public static final URL javaZoneComposition_WithoutImages = createURL("http://kpro09.googlecode.com/svn-history/r530/trunk/graph-gui-scala/src/main/resources/composition/javazone.dvl.composition.xml");
    public static final URL NULL = createURL("http://null.com");


    private static URL createURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("This should never happen!", e);
        }
    }
}
