package no.lau.vdvil.mix;

import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.instruction.ImageInstruction;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.TestMp3s;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class SurrenderPlayingTest {
    URL surrenderDvl;
    SuperPlayingSetup player;

    @Before
    public void before() {
        surrenderDvl = ClassLoader.getSystemResource("Way_Out_West-Surrender-Eelke_Kleijn_Remix.dvl.xml");
        player = new SuperPlayingSetup() {
            @Override
            protected Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
                List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
                parts.add(createImagePart("Diving Teddy", new Interval(0, 16), new URL("http://www.shinyshiny.tv/teddy%20bear%201.jpg")));
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
        };
    }

    @Test
    public void play() throws IOException {
        player.play(new MasterBeatPattern(12, 20, 150F));
    }

    @Test
    public void testTimeIntervalsProducedForInstructions() throws IOException {
        Composition composition = player.compose(new MasterBeatPattern(0, 64, 120F));
        composition.cache(player.downloader);
        Instructions instructions = composition.instructions(120F);
        List<Instruction> instructionList = instructions.lock();

        //ImageInstruction 0-16
        assertEquals(ImageInstruction.class, instructionList.get(0).getClass());
        assertEquals(0, instructionList.get(0).start());
        assertEquals(352800, instructionList.get(0).end()); //16 * 1/2 * 44100
        //AudioInstruction 0-16
        assertEquals(AudioInstruction.class, instructionList.get(1).getClass());
        assertEquals(0, instructionList.get(1).start());
        assertEquals(352800, instructionList.get(1).end());
        //ImageInstruction 16-32
        assertEquals(ImageInstruction.class, instructionList.get(2).getClass());
        assertEquals(352800, instructionList.get(2).start());
        assertEquals(705600, instructionList.get(2).end());
        //AudioInstruction 16-80
        assertEquals(AudioInstruction.class, instructionList.get(3).getClass());
        assertEquals(352800, instructionList.get(3).start());
        assertEquals(1763999, instructionList.get(3).end());
        //ImageInstruction
        assertEquals(ImageInstruction.class, instructionList.get(4).getClass());
        assertEquals(705600, instructionList.get(4).start());
        assertEquals(1058400, instructionList.get(4).end());
        //ImageInstruction
        assertEquals(ImageInstruction.class, instructionList.get(5).getClass());
        assertEquals(1411200, instructionList.get(5).start());
        assertEquals(2116800, instructionList.get(5).end());
        //AudioInstruction 144 - 240
        assertEquals(AudioInstruction.class, instructionList.get(6).getClass());
        assertEquals(1763999, instructionList.get(6).start());
        assertEquals(3175200, instructionList.get(6).end());

        assertEquals(AudioInstruction.class, instructionList.get(7).getClass());
        assertEquals(3175200, instructionList.get(7).start());
        assertEquals(5292000, instructionList.get(7).end());
    }
}