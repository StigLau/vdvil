package no.lau.vdvil.instruction;

/**
 * Abstract of sortable Instructions
 */
public class SortInstructionHelper {
    public static int compareTo(Object thisO, Object otherO) {
        Instruction a = (Instruction) otherO;
        Instruction b = (Instruction) thisO;
        if (a.start() < b.start())
            return 1;
        else if (a.start() > b.start())
            return -1;
        else {
            if (a.end() < b.end())
                return 1;
            else if (a.end() > b.end())
                return -1;
        }
        return 0;
    }
}
