package no.lau.vdvil.mix;

import no.bouvet.kpro.renderer.Instructions;
import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.PartXML;
import no.lau.vdvil.instruction.ImageInstruction;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.mix.util.SuperPlayingSetup;
import no.lau.vdvil.playback.PreconfiguredVdvilPlayer;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.AudioDescription;
import no.vdvil.renderer.audio.TestMp3s;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SurrenderPlayingTest extends SuperPlayingSetup {
    Store store = Store.get();
    FileRepresentation surrenderDvl;

    public void play() throws IOException, IllegalAccessException {
        Composition surrender = compose(new MasterBeatPattern(0, 32, 150F));
        Composition javaZone = (Composition) parser.parse(PartXML.create(TestMp3s.javaZoneComposition));
        vdvilPlayer.init(surrender);
        vdvilPlayer.addComposition(javaZone, new MasterBeatPattern(0, 32, 150F), 32);
        vdvilPlayer.play();
        vdvilPlayer.addComposition(surrender, new MasterBeatPattern(0, 64, 150F), 64);
        System.in.read(); //Wait for enter to be pressed
        vdvilPlayer.stop();
    }

    public static void main(String[] s) throws IOException, IllegalAccessException {
        new SurrenderPlayingTest().play();
    }

    @Test
    public void testTimeIntervalsProducedForInstructions() throws IOException {
        Composition composition = compose(new MasterBeatPattern(0, 256, 120F));
        PreconfiguredVdvilPlayer.cache(composition);
        Instructions instructions = composition.instructions(120F, null);
        List<Instruction> instructionList = instructions.lock();

        //ImageInstruction 0-16
        assertTrue(instructionList.get(0) instanceof ImageInstruction);
        assertEquals(0, instructionList.get(0).start());
        assertEquals(352800, instructionList.get(0).end()); //16 * 1/2 * 44100
        //AudioInstruction 0-16
        assertEquals(AudioInstruction.class, instructionList.get(1).getClass());
        assertEquals(0, instructionList.get(1).start());
        assertEquals(352800, instructionList.get(1).end());
        //ImageInstruction 16-32
        assertTrue(instructionList.get(2) instanceof ImageInstruction);
        assertEquals(352800, instructionList.get(2).start());
        assertEquals(705600, instructionList.get(2).end());
        //AudioInstruction 16-80
        assertEquals(AudioInstruction.class, instructionList.get(3).getClass());
        assertEquals(352800, instructionList.get(3).start());
        assertEquals(1764000, instructionList.get(3).end());
        //ImageInstruction
        assertTrue(instructionList.get(4) instanceof ImageInstruction);
        assertEquals(705600, instructionList.get(4).start());
        assertEquals(1058400, instructionList.get(4).end());
        //ImageInstruction
        assertTrue(instructionList.get(5) instanceof ImageInstruction);
        assertEquals(1411200, instructionList.get(5).start());
        assertEquals(2116800, instructionList.get(5).end());
        //AudioInstruction 144 - 240
        assertEquals(AudioInstruction.class, instructionList.get(6).getClass());
        assertEquals(1763999, instructionList.get(6).start());
        assertEquals(3175199, instructionList.get(6).end());

        assertEquals(AudioInstruction.class, instructionList.get(7).getClass());
        assertEquals(3175200, instructionList.get(7).start());
        assertEquals(5292000, instructionList.get(7).end());
    }

    @Test
    public void testIntegrityOfInstructions() throws IOException {
        Composition composition = compose(new MasterBeatPattern(0, 128, 130F));
        List<MultimediaPart> parts = composition.multimediaParts;
        assertEquals(0, ((AudioDescription) parts.get(5)).compositionInstruction.start());
        assertEquals(16, ((AudioDescription) parts.get(5)).compositionInstruction.duration());
        assertEquals(16, ((AudioDescription) parts.get(5)).compositionInstruction.end());

        assertEquals(16, ((AudioDescription) parts.get(6)).compositionInstruction.start());
        assertEquals(64, ((AudioDescription) parts.get(6)).compositionInstruction.duration());
        assertEquals(80, ((AudioDescription) parts.get(6)).compositionInstruction.end());

        assertEquals(80, ((AudioDescription) parts.get(7)).compositionInstruction.start());
        assertEquals(144, ((AudioDescription) parts.get(7)).compositionInstruction.end());
        assertEquals(64, ((AudioDescription) parts.get(7)).compositionInstruction.duration()); //Should be 48

        assertEquals(144, ((AudioDescription) parts.get(8)).compositionInstruction.start());
        assertEquals(96, ((AudioDescription) parts.get(8)).compositionInstruction.duration());
        assertEquals(240, ((AudioDescription) parts.get(8)).compositionInstruction.end());

        assertEquals(240, ((AudioDescription) parts.get(9)).compositionInstruction.start());
        assertEquals(64, ((AudioDescription) parts.get(9)).compositionInstruction.duration());
        assertEquals(304, ((AudioDescription) parts.get(9)).compositionInstruction.end());

        assertEquals(304, ((AudioDescription) parts.get(10)).compositionInstruction.start());
        assertEquals(128, ((AudioDescription) parts.get(10)).compositionInstruction.duration());
        assertEquals(432, ((AudioDescription) parts.get(10)).compositionInstruction.end());

    }


    public Composition compose(MasterBeatPattern masterBeatPattern) throws IOException {
        surrenderDvl = store.cache(ClassLoader.getSystemResource("Way_Out_West-Surrender-Eelke_Kleijn_Remix.dvl.xml"), "e4d263440e684878cf3b7d3ec1c44b46");
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

        return new Composition("SurrenderTest", masterBeatPattern, parts, surrenderDvl);
    }
}