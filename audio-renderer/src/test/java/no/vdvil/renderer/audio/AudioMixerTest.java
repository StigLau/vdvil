package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.bouvet.kpro.renderer.audio.AudioPlaybackTarget;
import no.bouvet.kpro.renderer.audio.MP3Source;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.instruction.Instruction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("IntegrationTest")
public class AudioMixerTest {
    AudioInstruction instruction;
    ShortBuffer source;
    final int duration = 4410;
    final int volume = 127;
    final AudioPlaybackTarget target = new AudioPlaybackTarget();
    final int maxSamplesForTest = 814150;
    final List<AudioInstruction> instructions = new ArrayList<>();


    int[] mix() { return new int[AudioMixer.MIX_FRAME * 2];}

    @BeforeEach
    public void setUp() throws IOException {
        FileRepresentation fileRepresentation = Store.get().cache(TestMp3s.returningMp3.toURL(), "3e3477a6ccba67aa9f3196390f48b67d");
        instruction = new AudioInstruction(0, maxSamplesForTest, 0, 81415, fileRepresentation);
        int internal = 0;
        int sduration = 4410;
        source = new MP3Source(fileRepresentation).getBuffer(instruction.getCue() + internal, sduration + 22050);
        instructions.add(instruction);
    }

    @Test
    public void testMixSampleOutput() {
        int[] mix = mix();
        AudioMixer.mix(source, duration, Instruction.RESOLUTION, volume, mix);
        assertEquals(8820, mix.length);
        assertEquals(0, mix[0]);
        assertEquals(129, mix[4535]);
        assertEquals(38, mix[8627]);
        assertEquals(50, mix[8819]);
    }
    
    @Test
    public void testSinglePass() {
        int[] mix = mix();
        int available = AudioMixer.singlePass(instruction, 0, mix);
        assertEquals(4410, available);
        assertEquals(8820, mix.length);
    }

    @Test
    public void testMixItUp() {
        int time = AudioMixer.mixItUp(instructions, 0, target);
        assertEquals(4410, time);
        int time2 = AudioMixer.mixItUp(instructions, Instruction.RESOLUTION, target);
        assertEquals(48510, time2);
    }

    @Test
    public void testOfActualPlaybackUsingJustTheMixer() {
        int time = maxSamplesForTest/2;
        System.out.println("Performing Nothing playback with " + getClass());
        while(time < maxSamplesForTest) {
            time = AudioMixer.mixItUp(instructions, time, target);
        }
    }
}
