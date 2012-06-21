package no.lau.vdvil.timing;

import no.lau.vdvil.renderer.Instruction;
import no.lau.vdvil.renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public class BeatRenderer implements Renderer {

    private List<ResolutionInstruction> instructions = new ArrayList<ResolutionInstruction>();
    private List<Renderer> renderers = new ArrayList<Renderer>();
    private ResolutionTimer parent;

    public void addInstruction(Instruction instruction) {
        this.instructions.add((ResolutionInstruction) instruction);
    }

    public void notify(long time) {
        for (Renderer renderer : renderers) {
            renderer.notify(convertToBeat(time));
        }
    }

    private int convertToBeat(long time) {
        ResolutionInstruction instruction = instructions.get(0);
        return (int) (time  * instruction.speed / (instruction.perMinute * parent.resolution()));
    }

    int calculateResolution(int speed, int perMinute) {
        return ResolutionTimer.resolution * perMinute / speed;
    }

    public void setParent(ResolutionTimer parentTimer) {
        this.parent = parentTimer;
        parentTimer.notifyEvery(this, calculateResolution(instructions.get(0).speed, instructions.get(0).perMinute));
    }

    public void addRenderer(Renderer renderer) {
        this.renderers.add(renderer);
    }
}
