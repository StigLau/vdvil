package no.vdvil.renderer.audio;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestMp3s {
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

    public static final Track returning = returning();
    private static URL createURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("This should never happen!", e);
        }
    }

    static Track returning() {
        MediaFile mediaFile = new MediaFile(createURL("http://kpro09.googlecode.com/svn/test-files/holden-nothing-93_returning_mix.mp3"), 5.945F, "3e3477a6ccba67aa9f3196390f48b67d");
        List<Segment> segments = new ArrayList<Segment>();
        segments.add(new Segment("4336519975847252321","low",  0, 64));
        segments.add(new Segment("4638184666682848978", "", 64, 128));
        segments.add(new Segment("2754708889643705332", "Up", 128, 256));
        segments.add(new Segment("4533227407229953527", "Down", 256, 320));
        segments.add(new Segment("6401936245564505757", "Setting up", 320, 416));
        segments.add(new Segment("30189981949854134", "Want nothing 1. time", 416, 448));
        segments.add(new Segment("6182122145512625145", "", 448, 480));
        segments.add(new Segment("6978423701190173373", "Action satisfaction", 480, 512));
        segments.add(new Segment("3657904262668647219", "Calming synth", 512, 544));
        segments.add(new Segment("3378726703924324403", "Lyrics - 1. part", 544, 576));
        segments.add(new Segment("4823965795648964701", "want nothing - 2. part", 576, 608));
        segments.add(new Segment("5560598317419002938", "want nothing - 2. time", 608, 640));
        segments.add(new Segment("9040781467677187716", "want nothing - 3. time", 640, 704));
        segments.add(new Segment("5762690949488488062", "synth", 704, 768));
        segments.add(new Segment("651352148519104110", "Want nothing - 3. time", 768, 1024));
        return new Track("Holden an Thompsen - Nothing", 130.0F, mediaFile, segments);
    }
}

