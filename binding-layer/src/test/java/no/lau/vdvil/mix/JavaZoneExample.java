package no.lau.vdvil.mix;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.mix.util.CompositionHelper;
import no.lau.vdvil.playback.BackStage;
import no.lau.vdvil.playback.VdvilWavConfig;
import no.lau.vdvil.timing.Interval;
import no.vdvil.renderer.audio.TestMp3s;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.timing.MasterBeatPattern;
import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class JavaZoneExample {
    FileRepresentation returning = TestMp3s.returningJsonDvl;
    FileRepresentation not_alone = TestMp3s.not_aloneDvl;
    FileRepresentation scares_me = TestMp3s.scares_meDvl;
    MasterBeatPattern mbp = new MasterBeatPattern(0, 32 + 64 * 3 + 28, 150F);

    @Test
    public void persistenceTest() throws IOException {
        VdvilWavConfig asFile = new VdvilWavConfig(this);
        new BackStage(asFile).prepare(composition).playUntilEnd();
        assertEquals("5a453d82ff2844a3caa716c533cca240", asFile.checksum());
    }
    public static void main(String[] args) throws Exception {
        new BackStage().prepare(new JavaZoneExample().composition).playUntilEnd();
    }

    Composition composition = new Composition(getClass().getSimpleName(), mbp, FileRepresentation.NULL, new CompositionHelper() {
        public List<MultimediaPart> parts() {
            return Arrays.asList(
                    createAudioPart("4479230163500364845", new Interval(0, 32), not_alone),
                    createAudioPart("5403996530329584526", new Interval(16, 32), scares_me),
                    createAudioPart("8313187524105777940", new Interval(32, 38), not_alone),
                    createAudioPart("5403996530329584526", new Interval(48, 16), scares_me),
                    createAudioPart("1826025806904317462", new Interval(64, 48), scares_me),
                    createAudioPart("6401936245564505757", new Interval(32 + 64, 44), returning),
                    createAudioPart("6401936245564505757", new Interval(32 + 64, 44), returning),
                    createAudioPart("6182122145512625145", new Interval(64 * 2, 46), returning),
                    createAudioPart("3378726703924324403", new Interval(16 + 64 * 2, 30), returning),
                    createAudioPart("4823965795648964701", new Interval(14 + 32 + 64 * 2, 1), returning),
                    createAudioPart("5560598317419002938", new Interval(15 + 32 + 64 * 2, 1), returning),
                    createAudioPart("9040781467677187716", new Interval(16 + 32 + 64 * 2, 64), returning),
                    createAudioPart("8301899110835906945", new Interval(16 + 64 * 3, 16), scares_me),
                    createAudioPart("5555459205073513470", new Interval(32 + 64 * 3, 28), scares_me));
        }
    });

    @Test
    public void testSanityOfTimingCalculation() throws IOException {
        List<Instruction> ins = composition.instructions(mbp.masterBpm, 0).lock();
        assertEquals(14, ins.size());
        printOutInstructions(ins);

        checkInstruction(ins.get(0), 0, 564480, 564480, 650475, 564480);
        checkInstruction(ins.get(1), 282240, 564480, 846720, 9676998, 564480);
        checkInstruction(ins.get(2), 564480, 670320, 1234800, 1912352, 670320);
        checkInstruction(ins.get(3), 846720, 282240, 1128960, 9676998, 282240);
        checkInstruction(ins.get(4), 1128960, 846719, 1975679, 12256495, 846719);
        checkInstruction(ins.get(5), 1693440, 776160, 2469600, 6775405, 776160);
        checkInstruction(ins.get(6), 1693440, 776160, 2469600, 6775405, 776160);
        checkInstruction(ins.get(7), 2257920, 811440, 3069360, 9380698, 811440);
        checkInstruction(ins.get(8), 2540160, 529200, 3069360, 11334666, 529200);
        checkInstruction(ins.get(9), 3069360, 17640, 3087000, 11985990, 17640);
        checkInstruction(ins.get(10), 3087000, 17640, 3104640, 12637312, 17640);
        checkInstruction(ins.get(11), 3104640, 1128960, 4233600, 13288636, 1128960);
        checkInstruction(ins.get(12), 3669120, 282239, 3951359, 22574484, 282239);
        checkInstruction(ins.get(13), 3951359, 493921, 4445280, 24831544, 493921);
    }

    private void printOutInstructions(List<Instruction> instructions) {
        for (int i = 0; i < instructions.size(); i++) {
            AudioInstruction ins = (AudioInstruction) instructions.get(i);
            System.out.println("checkInstruction(ins.get(" + i + "), " +
                    ins.start() + ", " + ins.length() + ", " + ins.end() + ", " + ins.getCue() + ", " + ins.getSourceDuration() + ");");
        }
    }

    private void checkInstruction(Instruction ins, int start, int length, int end, int cue, int sourceDuration) {
        AudioInstruction instruction = (AudioInstruction) ins;
        assertEquals(start, instruction.start());
        assertEquals(length, instruction.length());
        assertEquals(end, instruction.end());
        assertEquals(cue, instruction.getCue());
        assertEquals(sourceDuration, instruction.getSourceDuration());
    }
}
