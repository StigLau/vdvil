package no.vdvil.renderer.audio;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.bouvet.kpro.renderer.audio.AudioSource;
import no.bouvet.kpro.renderer.audio.AudioTarget;
import no.lau.vdvil.instruction.Instruction;
import static no.lau.vdvil.instruction.Instruction.RESOLUTION;
import java.nio.ShortBuffer;
import java.util.SortedSet;

public class AudioMixer {
    /**
     * The size of a audioMixer frame, in samples. 1/10th of a second.
     */
    public final static int MIX_FRAME = 4410;

    public static int mixItUp(SortedSet<Instruction> _active, int time, AudioTarget target) {
        int[] mix = new int[MIX_FRAME * 2];
        for (int fill = 0; fill < mix.length;) {
            mix[fill++] = 0;
        }

        int available = -1;
        for (Instruction instruction : _active) {
            if (instruction.start() > time) {
                available = ((Long) instruction.start()).intValue() - time;
            } else {
                available = singlePass((AudioInstruction) instruction, time, mix);
            }
        }
        return (available == -1) ?
                time :
                time + write(available, mix, target);
    }

    static int write(int available, int[] mix, AudioTarget target) {
        byte[] output = new byte[MIX_FRAME * 4];
            for (int convert = 0; convert < output.length;) {
                int v = mix[convert >>> 1];
                if (v > 32766)
                    v = 32766;
                else if (v < -32766)
                    v = -32766;
                output[convert++] = (byte) (v & 0xFF);
                output[convert++] = (byte) (v >>> 8);
            }

            return target.write(output, 0, available);
    }

    static int singlePass(final AudioInstruction instruction, int _time, int[] mix) {
        final AudioSource audioSource = instruction.getSource();
        Long dur = instruction.end() - _time;
        int duration = dur.intValue();

        if (instruction.getCacheExternal() != _time) {
            long external = instruction.start();
            long internal = 0;
            long frame = 0;

            while (external < _time) {
                long rate = instruction
                        .getInterpolatedRate((int) internal);
                frame = MIX_FRAME * rate / RESOLUTION;
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
        int sduration = duration * rate / RESOLUTION;

        instruction.advanceCache(duration, sduration);

        ShortBuffer source = audioSource.getBuffer(instruction.getCue() + internal, sduration + 22050);

        if (source != null) {
            mix(source, duration, rate, volume, mix);
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
     *            the rate to mix the source samples, relative to RESOLUTION
     * @param volume
     *            the volume to mix the source samples, relative to 127
     */
    static void mix(ShortBuffer source, int duration, int rate, int volume, int[] mix) {
        int base = source.position();

        for (int time = 0, output = 0; time < duration; time++) {
            int input = base + ((time * rate / RESOLUTION) << 1);

            mix[output++] += (source.get(input) * volume) >> 7;
            mix[output++] += (source.get(input + 1) * volume) >> 7;
        }
    }
}