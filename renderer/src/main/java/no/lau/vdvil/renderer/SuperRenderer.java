package no.lau.vdvil.renderer;


import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.RendererToken;
import no.lau.vdvil.handler.CompositionI;
import no.lau.vdvil.handler.InstructionInterface;
import no.lau.vdvil.timing.MasterBeatPattern;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

public abstract class SuperRenderer implements InstructionInterface, RendererToken {

    protected SortedSet<Instruction> instructionSet = new TreeSet<Instruction>();

    @Override
    public void setComposition(CompositionI composition, MasterBeatPattern beatPattern) {
        try {
            for (Instruction instruction : composition.instructions(beatPattern).lock()) {
                if(passesFilter(instruction)) {
                    instructionSet.add(instruction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Deprecated //Please use Composition insertion of instructions!
    public void addInstruction(Instruction instruction) {
        instructionSet.add(instruction);
    }

    /**
     * Callback to child to check if it is concerned with this object
     * @param instruction to be checked
     * @return true if it is to be added to InstructionList
     */
    abstract protected boolean passesFilter(Instruction instruction);

    public boolean isRendering() {
        return !instructionSet.isEmpty();
    }

    /**
     * Recursive method for finding a next Instruction which has a start which which has not passed yet
     * @param time currentTime
     * @param instructionList Must be sorted ascending by starttime
     * @return the first Instruction that has a startTime not passed yet. null
     */
    protected static Instruction findNextInstruction(int time, SortedSet<Instruction> instructionList) {
        if(instructionList.isEmpty())
            return null;
        Instruction first = instructionList.first();
        if(first._start >= time)
            return first;
        else
            return findNextInstruction(time, instructionList.headSet(first));
    }

    public void stop() {

    }
}
