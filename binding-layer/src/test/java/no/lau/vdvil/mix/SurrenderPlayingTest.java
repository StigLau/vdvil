package no.lau.vdvil.mix;

import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.instruction.ImageInstruction;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.mix.util.SuperPlayingSetup;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class SurrenderPlayingTest {
    Store store = Store.get();
    FileRepresentation surrenderDvl;
    SuperPlayingSetup player;

    @Before
    public void before() throws IOException {
        surrenderDvl = store.cache(ClassLoader.getSystemResource("Way_Out_West-Surrender-Eelke_Kleijn_Remix.dvl.xml"), "e4d263440e684878cf3b7d3ec1c44b46");
        player = new SuperPlayingSetup() {
            public Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
                List<MultimediaPart> parts = new ArrayList<MultimediaPart>();
                parts.add(createImagePart("Diving Teddy", new Interval(0, 16), store.createKey("http://www.shinyshiny.tv/teddy%20bear%201.jpg", "a9e178def69c92cc9355b1e7512dabe8")));
                parts.add(createImagePart("Dead Teddy", new Interval(16, 16), store.createKey("http://fc03.deviantart.net/fs17/f/2007/182/f/4/Dead_Teddy_by_Cast_Down_Doll.jpg", "4648c59ec6235407b59a0327328041b5")));
                parts.add(createImagePart("Diving Teddy", new Interval(32, 16), store.createKey("http://farm3.static.flickr.com/2095/2282261838_276a37d325_o_d.jpg", "5afcd12326717d727f694aba4d2e1055")));
                parts.add(createImagePart("Dead Teddy", new Interval(64, 32), store.createKey("http://fc03.deviantart.net/fs17/f/2007/182/f/4/Dead_Teddy_by_Cast_Down_Doll.jpg", "Dootsie")));
                parts.add(createImagePart("Diving Teddy", new Interval(240, 4), store.createKey("http://www.shinyshiny.tv/teddy%20bear%201.jpg", "hey")));

                parts.add(createAudioPart("0-128Surrender",   new Interval(0, 16), surrenderDvl));
                parts.add(createAudioPart("256-352Surrender", new Interval(16, 64), surrenderDvl)); // Elguitar
                parts.add(createAudioPart("352-512Surrender", new Interval(16+64, 64), surrenderDvl));
                parts.add(createAudioPart("352-512Surrender", new Interval(16+64*2, 64+32), surrenderDvl));
                parts.add(createAudioPart("480-544Surrender", new Interval(16+32+64*3, 64), surrenderDvl)); // Synth X2
                parts.add(createAudioPart("768-896Surrender", new Interval(16+32+64*4, 128), surrenderDvl));

                return new Composition("SurrenderTest", masterBeatPattern, parts, FileRepresentation.NULL);
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
        //TODO Cache Composition?
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