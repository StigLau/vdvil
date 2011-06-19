package no.lau.vdvil;

import static no.bouvet.kpro.mix.JavaZoneExample.createAudioPart;

import com.sun.tools.corba.se.idl.toJavaPortable.StringGen;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.persistence.PartXML;
import no.vdvil.renderer.audio.AudioDescription;
import no.vdvil.renderer.audio.TestMp3s;
import no.vdvil.renderer.audio.Track;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlayingAMixTest2 {
    Track returning = TestMp3s.returning;
    URL psylteDvl = TestMp3s.psylteDvl;
    PreconfiguredVdvilPlayer vdvilPlayer;

    @Test
    public void play() throws Exception {
        vdvilPlayer = new PreconfiguredVdvilPlayer();
        vdvilPlayer.init(compose(new MasterBeatPattern(0, 16, 130F)));
        vdvilPlayer.play(0);
        while(vdvilPlayer.isPlaying())
            Thread.sleep(200);
    }

    Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
        DownloadAndParseFacade downloader = vdvilPlayer.accessCache();
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(createAudioPart(returning.segments.get(3).id, 0, 20, TestMp3s.returningDvl, downloader));
        //parts.add(createAudioPart("", 8, 16, psylteDvl, vdvilPlayer.accessCache())); //psylteFlesk.segments.get(4)
        parts.add(createAudioPart(returning.segments.get(6).id, 12, 52, TestMp3s.returningDvl, downloader));
        parts.add(createAudioPart(returning.segments.get(3).id, 20, 32, TestMp3s.returningDvl, downloader));
        return new Composition(getClass().getSimpleName(), masterBeatPattern, parts, TestMp3s.NULL);
    }
}