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
import no.lau.vdvil.mix.util.CompositionHelper;
import no.lau.vdvil.playback.BackStage;
import no.lau.vdvil.playback.VdvilAudioConfig;
import no.lau.vdvil.player.InstructionPlayer;
import no.lau.vdvil.timing.Interval;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.vdvil.renderer.audio.AudioDescription;
import no.vdvil.renderer.audio.TestMp3s;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SurrenderPlayingTest {
    final Store store = Store.get();
    FileRepresentation surrenderDvl;

    public SurrenderPlayingTest() throws IOException {
        surrenderDvl = store.cache(ClassLoader.getSystemResource("Way_Out_West-Surrender-Eelke_Kleijn_Remix.dvl.xml"), "fc75a9899ee6082d769ea35941d3a36a");
    }

    public void play() throws IOException {
        BackStage backStage = new BackStage();
        Composition surrender = createComposition(new MasterBeatPattern(0, 32, 150F));
        Composition javaZone = (Composition) new VdvilAudioConfig().getParseFacade().parse(PartXML.create(TestMp3s.javaZoneComposition));
        InstructionPlayer vdvilPlayer = (InstructionPlayer) backStage.prepare(surrender);
        backStage.addComposition(vdvilPlayer, javaZone, new MasterBeatPattern(0, 32, 150F), 32);
        vdvilPlayer.play();
        backStage.addComposition(vdvilPlayer, surrender, new MasterBeatPattern(0, 64, 150F), 64);
        System.in.read(); //Wait for enter to be pressed
        vdvilPlayer.stop();
    }
/*
    public static void main(String[] s) throws IOException, IllegalAccessException {
        new SurrenderPlayingTest().play();
    }
    */
    @Test
    public void testTimeIntervalsProducedForInstructions() throws IOException {
        Composition composition = createComposition(new MasterBeatPattern(0, 256, 120F));
        BackStage.cache(composition);
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
    public void testIntegrityOfInstructions() {
        Composition composition = createComposition(new MasterBeatPattern(0, 128, 130F));
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


    Composition createComposition(MasterBeatPattern mbp) {
        return new Composition(getClass().getSimpleName(), mbp, FileRepresentation.NULL, new CompositionHelper() {
            public List<MultimediaPart> parts() {
                return Arrays.asList(
                        createImagePart("Diving Teddy", new Interval(0, 16), store.createKey("https://s3.amazonaws.com/dvl-test-music/test-images/teddy/teddy+bear+1.jpg", "a9e178def69c92cc9355b1e7512dabe8")),
                        createImagePart("Dead Teddy", new Interval(16, 16), store.createKey("https://dvl-test-music.s3.amazonaws.com/test-images/teddy/Dead_Teddy_by_Cast_Down_Doll.jpg", "4648c59ec6235407b59a0327328041b5")),
                        createImagePart("Diving Teddy", new Interval(32, 16), store.createKey("https://dvl-test-music.s3.amazonaws.com/test-images/teddy/cute-teddy.jpg", "5afcd12326717d727f694aba4d2e1055")),
                        createImagePart("Dead Teddy", new Interval(64, 32), store.createKey("https://dvl-test-music.s3.amazonaws.com/test-images/teddy/Dead_Teddy_by_Cast_Down_Doll.jpg", "Dootsie")),
                        createImagePart("Diving Teddy", new Interval(240, 4), store.createKey("https://s3.amazonaws.com/dvl-test-music/test-images/teddy/teddy+bear+1.jpg", "hey")),

                        createAudioPart("0-128Surrender", new Interval(0, 16), surrenderDvl),
                        createAudioPart("256-352Surrender", new Interval(16, 64), surrenderDvl), // Elguitar
                        createAudioPart("352-512Surrender", new Interval(16 + 64, 64), surrenderDvl),
                        createAudioPart("352-512Surrender", new Interval(16 + 64 * 2, 64 + 32), surrenderDvl),
                        createAudioPart("480-544Surrender", new Interval(16 + 32 + 64 * 3, 64), surrenderDvl), // Synth X2
                        createAudioPart("768-896Surrender", new Interval(16 + 32 + 64 * 4, 128), surrenderDvl));
            }
        });
    }
}