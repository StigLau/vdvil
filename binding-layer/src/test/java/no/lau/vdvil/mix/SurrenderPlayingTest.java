package no.lau.vdvil.mix;

import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
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
        super.play(new MasterBeatPattern(0, 32, 150F)); //(0, 308, 125)
    }

    @Override
    protected Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        parts.add(createImagePart("Diving Teddy", 0, 4, new URL("http://www.shinyshiny.tv/teddy%20bear%201.jpg")));
        parts.add(createImagePart("Dead Teddy", 16, 20, new URL("http://fc03.deviantart.net/fs17/f/2007/182/f/4/Dead_Teddy_by_Cast_Down_Doll.jpg")));
        parts.add(createImagePart("Diving Teddy", 80, 84, new URL("http://farm3.static.flickr.com/2095/2282261838_276a37d325_o_d.jpg")));
        parts.add(createImagePart("Dead Teddy", 144, 148, new URL("http://fc03.deviantart.net/fs17/f/2007/182/f/4/Dead_Teddy_by_Cast_Down_Doll.jpg")));
        parts.add(createImagePart("Diving Teddy", 240, 244, new URL("http://www.shinyshiny.tv/teddy%20bear%201.jpg")));

        parts.add(createAudioPart("0-128Surrender", 0, 16, surrenderDvl, downloader));
        parts.add(createAudioPart("256-352Surrender", 16, 80, surrenderDvl, downloader)); // Elguitar
        parts.add(createAudioPart("352-512Surrender", 80, 144, surrenderDvl, downloader));
        parts.add(createAudioPart("352-512Surrender", 144, 240, surrenderDvl, downloader));
        parts.add(createAudioPart("480-544Surrender", 240, 304, surrenderDvl, downloader)); // Synth X2
        parts.add(createAudioPart("768-896Surrender", 304, 432, surrenderDvl, downloader));

        return new Composition("SurrenderTest", masterBeatPattern, parts, TestMp3s.NULL);
    }
}