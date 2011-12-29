package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.bouvet.kpro.renderer.audio.MP3Source;
import no.lau.vdvil.cache.SimpleCacheImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.ShortBuffer;

import static junit.framework.Assert.assertEquals;

public class AudioMixerTest {

    //A random selection of samples to check if code does right
    @Test
    public void testSchnuff() {
        int duration = 4410;
        int rate = 44100;
        int volume = 127;
        int[] mix = new AudioMixer(null).mix;
        AudioMixer.mix(source, duration, rate, volume, mix);
        assertEquals(8820, mix.length);
        assertEquals(0, mix[0]);
        assertEquals(129, mix[4535]);
        assertEquals(38, mix[8627]);
        assertEquals(50, mix[8819]);


    }

    ShortBuffer source;
    
    @Before
    public void setUp() throws IOException {
        File file = new SimpleCacheImpl().fileLocation(TestMp3s.returningMp3);
        AudioInstruction instruction = new AudioInstruction(0, 1000, new MP3Source(file), 0, 1000 );
        int internal = 0;
        int sduration = 4410;
        source = instruction.getSource().getBuffer(instruction.getCue() + internal, sduration + 22050);
    }
}
