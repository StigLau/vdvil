package no.bouvet.kpro.renderer;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class InstructionsTest {

    @Test
    public void testSortingByStart() {
        List<Instruction> sortedList = testInstructions()._list;
        assertEquals("1-3", sortedList.get(0).toString());
        assertEquals("1-5", sortedList.get(1).toString());
        assertEquals("1-6", sortedList.get(2).toString());
        assertEquals("2-8", sortedList.get(3).toString());
        assertEquals("3-5", sortedList.get(4).toString());
        assertEquals("4-4", sortedList.get(5).toString());
        assertEquals("5-5", sortedList.get(6).toString());
        assertEquals("6-5", sortedList.get(7).toString());
    }

    @Test
    public void testSortingByEnd() {
        List<Instruction> sortedList = testInstructions().sortedByEnd();
        
        assertEquals("1-3", sortedList.get(0).toString());
        assertEquals("4-4", sortedList.get(1).toString());
        assertEquals("1-5", sortedList.get(2).toString());
        assertEquals("3-5", sortedList.get(3).toString());
        assertEquals("5-5", sortedList.get(4).toString());
        assertEquals("6-5", sortedList.get(5).toString());
        assertEquals("1-6", sortedList.get(6).toString());
        assertEquals("2-8", sortedList.get(7).toString());
    }

    /*
     * Trying to hit all the different problem possibilities
     */
    Instructions testInstructions() {
        Instructions instructions = new Instructions();
        instructions.append(new TestInstruction(6, 5));
        instructions.append(new TestInstruction(3, 5));
        instructions.append(new TestInstruction(1, 5));
        instructions.append(new TestInstruction(4, 4));
        instructions.append(new TestInstruction(1, 6));
        instructions.append(new TestInstruction(1, 3));
        instructions.append(new TestInstruction(2, 8));
        instructions.append(new TestInstruction(5, 5));
        return instructions;
    }
}

class TestInstruction extends Instruction {
    public TestInstruction(int start, int end) {
        super(start, end);
    }

    public String toString() {
        return _start + "-" + _end;
    }
}
