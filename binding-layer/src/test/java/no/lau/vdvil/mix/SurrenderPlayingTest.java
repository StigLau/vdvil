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
        super.downloader.setRefreshCaches(true);
        super.play(new MasterBeatPattern(0, 544, 150F));
    }

    @Override
    protected Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
        List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
        /*
        parts.add(createAudioPart("0-128Surrender", 0, 128, surrenderDvl, downloader)); // Intro -- Buildup
        parts.add(createAudioPart("128-256Surrender", 128, 256, surrenderDvl, downloader)); // AAaaaaaah -- Start -- Flurry  <-- Can be cut up!
        parts.add(createAudioPart("256-352Surrender", 256, 352, surrenderDvl, downloader)); // Elguitar
        parts.add(createAudioPart("352-512Surrender", 352, 480, surrenderDvl, downloader)); // Just surrender x2
        parts.add(createAudioPart("480-544Surrender", 480, 544, surrenderDvl, downloader)); // Synth X2
        parts.add(createAudioPart("544-656Surrender", 544, 656, surrenderDvl, downloader)); // Soft song duette + flurry --
        parts.add(createAudioPart("656-768Surrender", 656, 768, surrenderDvl, downloader)); // Flurry butterflies
        parts.add(createAudioPart("768-912Surrender", 768, 912, surrenderDvl, downloader));
        */

        parts.add(createAudioPart("0-128Surrender", 0, 32, surrenderDvl, downloader));
        parts.add(createImagePart(4, 8, new URL("http://www.shinyshiny.tv/teddy%20bear%201.jpg")));
        parts.add(createAudioPart("256-352Surrender", 32, 96, surrenderDvl, downloader)); // Elguitar
        parts.add(createImagePart(8, 16, new URL("http://fc03.deviantart.net/fs17/f/2007/182/f/4/Dead_Teddy_by_Cast_Down_Doll.jpg")));
        parts.add(createAudioPart("352-512Surrender", 96, 160, surrenderDvl, downloader));
        parts.add(createAudioPart("352-512Surrender", 160, 256, surrenderDvl, downloader));
        parts.add(createAudioPart("480-544Surrender", 256, 320, surrenderDvl, downloader)); // Synth X2

        return new Composition("SurrenderTest", masterBeatPattern, parts, TestMp3s.NULL);
    }
}