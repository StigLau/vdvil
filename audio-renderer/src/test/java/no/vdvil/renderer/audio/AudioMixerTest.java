package no.vdvil.renderer.audio;

import com.google.common.collect.Sets;
import no.bouvet.kpro.renderer.OldRenderer;
import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.bouvet.kpro.renderer.audio.AudioPlaybackTarget;
import no.bouvet.kpro.renderer.audio.MP3Source;
import no.lau.vdvil.cache.SimpleCacheImpl;
import no.lau.vdvil.instruction.Instruction;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.util.TreeSet;

import static junit.framework.Assert.assertEquals;

public class AudioMixerTest {
    AudioInstruction instruction;
    ShortBuffer source;
    int duration = 4410;
    int volume = 127;
    AudioMixer mixer = new AudioMixer(new AudioPlaybackTarget());
    int maxSamplesForTest = 814150;
    TreeSet<Instruction> instructions = Sets.newTreeSet();


    @Before
    public void setUp() throws IOException {
        File file = new SimpleCacheImpl().fileLocation(TestMp3s.returningMp3);
        instruction = new AudioInstruction(0, maxSamplesForTest, new MP3Source(file), 0, 81415 );
        int internal = 0;
        int sduration = 4410;
        source = instruction.getSource().getBuffer(instruction.getCue() + internal, sduration + 22050);
        instructions.add(instruction);
    }

    @Test
    public void testMixSampleOutput() {
        int[] mix = mixer.mix;
        AudioMixer.mix(source, duration, OldRenderer.RATE, volume, mix);
        assertEquals(8820, mix.length);
        assertEquals(0, mix[0]);
        assertEquals(129, mix[4535]);
        assertEquals(38, mix[8627]);
        assertEquals(50, mix[8819]);
    }
    
    @Test
    public void testSinglePass() {
        int available = AudioMixer.singlePass(instruction, 0, mixer);
        assertEquals(4410, available);
        assertEquals(8820, mixer.mix.length);
    }

    @Test
    public void testMixItUp() {
        int time = AudioMixer.mixItUp(instructions, 0, mixer);
        assertEquals(4410, time);
        int time2 = AudioMixer.mixItUp(instructions, OldRenderer.RATE, mixer);
        assertEquals(48510, time2);
    }

    @Test
    public void testOfActualPlaybackUsingJustTheMixer() {
        int time = maxSamplesForTest/2;
        System.out.println("Performing Nothing playback with " + getClass());
        while(time < maxSamplesForTest) {
            time = AudioMixer.mixItUp(instructions, time, mixer);
        }
    }
}
