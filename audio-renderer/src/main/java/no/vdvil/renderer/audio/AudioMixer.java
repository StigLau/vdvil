package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;
import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.bouvet.kpro.renderer.audio.AudioTarget;

import java.nio.ShortBuffer;
import java.util.SortedSet;

public class AudioMixer {
    /**
     * The size of a audioMixer frame, in samples. 1/10th of a second.
     */
    public final static int MIX_FRAME = 4410;

    byte[] output = new byte[MIX_FRAME * 4];
    int[] mix = new int[MIX_FRAME * 2];
    int available = -1;
    public final AudioTarget target;

    public AudioMixer(AudioTarget target) {
        this.target = target;
    }


    public static int mixItUp(SortedSet<Instruction> _active, int time, AudioMixer audioMixer) {
        for (int fill = 0; fill < audioMixer.mix.length;) {
            audioMixer.mix[fill++] = 0;
        }

        for (Instruction instruction : _active) {
            if (instruction._start > time)
                audioMixer.available = instruction._start - time;
            else
                audioMixer.available = singlePass((AudioInstruction) instruction, time, audioMixer);
        }
        if (audioMixer.available > 0) {
            for (int convert = 0; convert < audioMixer.output.length;) {
                int v = audioMixer.mix[convert >>> 1];
                if (v > 32766)
                    v = 32766;
                else if (v < -32766)
                    v = -32766;
                audioMixer.output[convert++] = (byte) (v & 0xFF);
                audioMixer.output[convert++] = (byte) (v >>> 8);
            }

            int wrote = audioMixer.target.write(audioMixer.output, 0, audioMixer.available);
            time += wrote;
        }
        return time;
    }

    static int singlePass(final AudioInstruction instruction, int _time, AudioMixer audioMixer) {
        int duration = instruction._end - _time;

        if (instruction.getCacheExternal() != _time) {
            long external = instruction._start;
            long internal = 0;
            long frame = 0;

            while (external < _time) {
                long rate = instruction
                        .getInterpolatedRate((int) internal);
                frame = MIX_FRAME * rate / Renderer.RATE;
                internal += frame;
                external += MIX_FRAME;
            }

            if (external > _time) {
                internal -= frame;
                external -= MIX_FRAME;

                int error = _time - (int) external;
                if (error < duration)
                    duration = error;

                internal += error * frame / MIX_FRAME;
            } else {
                instruction.setCacheExternal(_time);
            }

            instruction.setCacheInternal((int) internal);
        }
        int available;
        if (duration > MIX_FRAME) {
            duration = MIX_FRAME;
            available = MIX_FRAME;
        }
        else
            available = duration;

        int internal = instruction.getCacheInternal();
        int rate = instruction.getInterpolatedRate(internal);
        int volume = instruction.getInterpolatedVolume(internal);
        int sduration = duration * rate / Renderer.RATE;

        instruction.advanceCache(duration, sduration);

        ShortBuffer source = instruction.getSource().getBuffer(
                instruction.getCue() + internal, sduration + 22050);

        if (source != null) {
            mix(source, duration, rate, volume, audioMixer.mix);
        }
        return available;
    }

    /**
     * Mix a range of audio samples into the mix buffer.
     *
     * @param source
     *            the source audio samples, in a pre-positioned ShortBuffer
     * @param duration
     *            the number of samples to mix, in output time
     * @param rate
     *            the rate to mix the source samples, relative to Renderer.RATE
     * @param volume
     *            the volume to mix the source samples, relative to 127
     */
    static void mix(ShortBuffer source, int duration, int rate, int volume, int[] mix) {
        int base = source.position();

        for (int time = 0, output = 0; time < duration; time++) {
            int input = base + ((time * rate / Renderer.RATE) << 1);

            mix[output++] += (source.get(input) * volume) >> 7;
            mix[output++] += (source.get(input + 1) * volume) >> 7;
        }
    }
}