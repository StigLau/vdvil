package no.vdvil.renderer.audio;

import no.lau.vdvil.cache.CacheMetaData;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.lau.vdvil.handler.persistence.DvlXML;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.timing.Interval;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestMp3s {
    public static final URL coronamp3 =             createURL("https://s3.amazonaws.com/dvl-test-music/music/Corona_-_Baby_Baby.mp3");
    public static final URL returningMp3 =          createURL("https://s3.amazonaws.com/dvl-test-music/music/holden-nothing-93_returning_mix.mp3");

    public static final FileRepresentation psylteDvl =              createFileRepresentation("https://s3.amazonaws.com/dvl-test-music/dvl/loaderror-psylteflesk.dvl", "11qqw");
    public static final FileRepresentation returningDvl =           createFileRepresentation("https://s3.amazonaws.com/dvl-test-music/dvl/holden-nothing-93_returning_mix.dvl", "7a7051b2295481de6d741c83fe194708");
    public static final FileRepresentation returningJsonDvl =       createFileRepresentation("https://raw.github.com/StigLau/vdvil/master/audio-renderer/src/test/resources/Returning.dvl.json", "2c118b9104462f6e8888c4aaf316cc6a");
    public static final FileRepresentation unfinishedSympathyDvl =  createFileRepresentation("https://s3.amazonaws.com/dvl-test-music/dvl/unfinished_sympathy.dvl", "123");
    public static final FileRepresentation not_aloneDvl =           createFileRepresentation("https://s3.amazonaws.com/dvl-test-music/dvl/olive-youre_not_alone.dvl", "94d57a015484d1019f0458c575a199e3");
    public static final FileRepresentation scares_meDvl =           createFileRepresentation("https://s3.amazonaws.com/dvl-test-music/dvl/christian_cambas-it_scares_me.dvl", "c65b216c36a7fe55df539142bcd9cf1b");
    public static final FileRepresentation spaceDvl =               createFileRepresentation("https://s3.amazonaws.com/dvl-test-music/dvl/space_manoeuvres-stage_one_original.dvl", "8262405fb97d00ebdb0ba92fcf727e55");
    public static final FileRepresentation surrenderDvl =           createFileRepresentation("https://raw.github.com/StigLau/vdvil/master/binding-layer/src/test/resources/Way_Out_West-Surrender-Eelke_Kleijn_Remix.dvl.xml", "fc75a9899ee6082d769ea35941d3a36a");

    private static FileRepresentation createFileRepresentation(String remoteLocation, String md5Checksum) {
        try {
            URL url = new URL(remoteLocation);
            return new CacheMetaData(url, md5Checksum);
        } catch (MalformedURLException e) {
            throw new RuntimeException("This should never happen!", e);
        }
    }

    public static final FileRepresentation javaZoneComposition = createFileRepresentation("https://s3.amazonaws.com/dvl-test-music/composition/javazone.dvl.composition.xml", "490aa410679405eebbe36b682db9547e");
    public static final FileRepresentation javaZoneCompositionJson = createFileRepresentation("https://heap.kompo.st/JavaZone_Demo", "45657af53365567c60e47410f4620678");
    public static final FileRepresentation javaZoneComposition_WithoutImages = createFileRepresentation("https://s3.amazonaws.com/dvl-test-music/composition/javazone.dvl.composition.xml", "c6c3b27b83e71c79574c1837062e2345");
    public static final URL NULL = createURL("https://null.com");

    public static final Track returning = returning();
    public static final Track corona = corona();

    private static URL createURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("This should never happen!", e);
        }
    }

    static Track returning() {
        MediaFile mediaFile = new MediaFile(returningMp3, 5.945F, "3e3477a6ccba67aa9f3196390f48b67d");
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment("4336519975847252321",  0, "low", 64));
        segments.add(new Segment("4638184666682848978", 64, "", 64));
        segments.add(new Segment("2754708889643705332", 128, "Up", 128));
        segments.add(new Segment("4533227407229953527", 256, "Down", 64));
        segments.add(new Segment("6401936245564505757", 320, "Setting up", 96));
        segments.add(new Segment("30189981949854134", 416, "Want nothing 1. time", 32));
        segments.add(new Segment("6182122145512625145", 448, "", 32));
        segments.add(new Segment("6978423701190173373", 480, "Action satisfaction", 32));
        segments.add(new Segment("3657904262668647219", 512, "Calming synth", 32));
        segments.add(new Segment("3378726703924324403", 544, "Lyrics - 1. part", 32));
        segments.add(new Segment("4823965795648964701", 576, "want nothing - 2. part", 32));
        segments.add(new Segment("5560598317419002938", 608, "want nothing - 2. time", 32));
        segments.add(new Segment("9040781467677187716", 640, "want nothing - 3. time", 64));
        segments.add(new Segment("5762690949488488062", 704, "synth", 64));
        segments.add(new Segment("651352148519104110", 768, "Want nothing - 3. time", 256));
        return new Track("Holden an Thompsen - Nothing", 130.0F, mediaFile, segments);
    }

    static Track corona() {
        MediaFile mediaFile = new MediaFile(coronamp3, Instruction.RESOLUTION * 0.445f, "e0e5beecd6a34f6a8ebae2c8840769af");
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment("a", 0, "Baby, why can't we just stay together", 16));
        segments.add(new Segment("b", 16, "Baby, why can't we just stay together", 16));
        segments.add(new Segment("c", 32, "Intro", 32));
        segments.add(new Segment("d", 64, "Riff 1. time", 32));
        segments.add(new Segment("e", 96, "1. Refrain  I want to roll inside your soul,", 32));
        segments.add(new Segment("f", 128, "2. Verse - Caught you down by suprise", 32));
        segments.add(new Segment("g", 128 + 32, "Baby baby, why can't we just stay together", 32));
        segments.add(new Segment("h", 128 + 64, "riff 2. time", 32));
        segments.add(new Segment("i", 128 + 96, "Deep inside I know you need it", 32));
        segments.add(new Segment("j", 256, "Caught you down by suprise", 32));
        return new Track("Corona - Baby baby", 132.98f, mediaFile, segments);
    }

    public static CompositionInstruction compInstructionFactory(FileRepresentation fileRepresentation, int start, int duration) {
        return new PartXML("Stub ID", new Interval(start, duration), new DvlXML(fileRepresentation));

    }
}

