package no.lau.vdvil.mix;

import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.TestMp3s;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SurrenderPlayingTest extends SuperPlayingSetup {
    URL surrenderDvl;
    @Test
    public void play() throws IOException {
        surrenderDvl = ClassLoader.getSystemResource("Way_Out_West-Surrender-Eelke_Kleijn_Remix.dvl.xml");
        super.play(new MasterBeatPattern(0, 16, 150F));
    }

    @Override
    protected Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(createImagePart("Diving Teddy", new Interval(0, 14), new URL("http://www.shinyshiny.tv/teddy%20bear%201.jpg")));
        parts.add(createImagePart("Dead Teddy", new Interval(16, 16), new URL("http://fc03.deviantart.net/fs17/f/2007/182/f/4/Dead_Teddy_by_Cast_Down_Doll.jpg")));
        parts.add(createImagePart("Diving Teddy", new Interval(32, 16), new URL("http://farm3.static.flickr.com/2095/2282261838_276a37d325_o_d.jpg")));
        parts.add(createImagePart("Dead Teddy", new Interval(64, 32), new URL("http://fc03.deviantart.net/fs17/f/2007/182/f/4/Dead_Teddy_by_Cast_Down_Doll.jpg")));
        parts.add(createImagePart("Diving Teddy", new Interval(240, 4), new URL("http://www.shinyshiny.tv/teddy%20bear%201.jpg")));

        parts.add(createAudioPart("0-128Surrender",   new Interval(0, 16), surrenderDvl, downloader));
        parts.add(createAudioPart("256-352Surrender", new Interval(16, 64), surrenderDvl, downloader)); // Elguitar
        parts.add(createAudioPart("352-512Surrender", new Interval(16+64, 64), surrenderDvl, downloader));
        parts.add(createAudioPart("352-512Surrender", new Interval(16+64*2, 64+32), surrenderDvl, downloader));
        parts.add(createAudioPart("480-544Surrender", new Interval(16+32+64*3, 64), surrenderDvl, downloader)); // Synth X2
        parts.add(createAudioPart("768-896Surrender", new Interval(16+32+64*4, 128), surrenderDvl, downloader));

        return new Composition("SurrenderTest", masterBeatPattern, parts, TestMp3s.NULL);
    }
}