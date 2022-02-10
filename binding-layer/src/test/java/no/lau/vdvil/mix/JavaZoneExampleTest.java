package no.lau.vdvil.mix;

import no.bouvet.kpro.renderer.audio.AudioInstruction;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.playback.BackStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Stig@Lau.no - 29/12/14.
 */
@Tag("IntegrationTest")
public class JavaZoneExampleTest {
    final JavaZoneExample jz = new JavaZoneExample();

    @BeforeEach
    public void setUp() throws IOException {
        BackStage.cache(jz.composition);
    }

    @Test
    public void testSanityOfTimingCalculation() {
        List<Instruction> ins = jz.composition.instructions(jz.mbp.masterBpm, 0).lock();
        assertEquals(14, ins.size());
        printOutInstructions(ins);

        checkInstruction(ins.get(0), 0, 564480, 564480, 650475, 564480);
        checkInstruction(ins.get(1), 282240, 564480, 846720, 9676998, 564480);
        checkInstruction(ins.get(2), 564480, 670320, 1234800, 1912352, 670320);
        checkInstruction(ins.get(3), 846720, 282240, 1128960, 9676998, 282240);
        checkInstruction(ins.get(4), 1128960, 846720, 1975680, 12256495, 846720);
        checkInstruction(ins.get(5), 1693440, 776160, 2469600, 6775405, 776160);
        checkInstruction(ins.get(6), 1693440, 776160, 2469600, 6775405, 776160);
        checkInstruction(ins.get(7), 2257920, 811439, 3069359, 9380698, 811439);
        checkInstruction(ins.get(8), 2540160, 529200, 3069360, 11334666, 529200);
        checkInstruction(ins.get(9), 3069360, 17640, 3087000, 11985990, 17640);
        checkInstruction(ins.get(10), 3087000, 17640, 3104640, 12637312, 17640);
        checkInstruction(ins.get(11), 3104640, 1128960, 4233600, 13288636, 1128960);
        checkInstruction(ins.get(12), 3669120, 282240, 3951360, 22574484, 282240);
        checkInstruction(ins.get(13), 3951359, 493919, 4445278, 24831544, 493919);
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
