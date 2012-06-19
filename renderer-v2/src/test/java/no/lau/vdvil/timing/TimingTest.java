package no.lau.vdvil.timing;

import no.lau.vdvil.renderer.Instruction;
import no.lau.vdvil.renderer.MetronomeInstruction;
import no.lau.vdvil.renderer.MetronomeRenderer;
import no.lau.vdvil.renderer.Renderer;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TimingTest {

    @Test
    public void buildCase() {
        PushByHandClock clock = new PushByHandClock();
        UserTimer parentTimer = new UserTimer(clock);
        Renderer metronome = new MetronomeRenderer();


        int resolution = calculateResolution(120, 60);
        parentTimer.notifyEvery(metronome, resolution);
        //The clock has been started
        assertEquals(0, parentTimer.checkBeat());

        clock.currentTimeMillis = 1;
        assertEquals(0, parentTimer.checkBeat());

        clock.currentTimeMillis = 499;
        assertEquals(0, parentTimer.checkBeat());

        clock.currentTimeMillis = 501;
        assertEquals(500, parentTimer.checkBeat());
        assertEquals(500, parentTimer.lastBeat);
        clock.currentTimeMillis = 1; //skipping back in time requires rewinding the lastBeat!
        assertEquals(500, parentTimer.lastBeat);

        //TODO Make so that milliseconds are translated into beats by an intermidi timer!
    }

    static int calculateResolution(int speed, int perMinute) {
        return UserTimer.resolution * perMinute / speed;
    }

    @Test
    public void testBeatCalculation() {
        PushByHandClock clock = new PushByHandClock();
        UserTimer parentTimer = new UserTimer(clock);
        BeatRenderer beatRenderer = new BeatRenderer();
        beatRenderer.addInstruction(new ResolutionInstruction(0, 64, 120, 60));
        beatRenderer.setParent(parentTimer);
        parentTimer.notifyEvery(beatRenderer, calculateResolution(120, 60));

        MetronomeRenderer metronome = new MetronomeRenderer();
        metronome.addInstruction(new MetronomeInstruction(0, 128));

        beatRenderer.addRenderer(metronome);


        parentTimer.checkBeat();
        clock.currentTimeMillis = 500;
        parentTimer.checkBeat();
        clock.currentTimeMillis = 1000;
        parentTimer.checkBeat();
        clock.currentTimeMillis = 1500;
        parentTimer.checkBeat();
        clock.currentTimeMillis = 2000;
        parentTimer.checkBeat();
    }
}

class BeatRenderer implements Renderer{

    private List<ResolutionInstruction> instructions = new ArrayList<ResolutionInstruction>();
    private List<Renderer> renderers = new ArrayList<Renderer>();
    private UserTimer parent;

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

    public void setParent(UserTimer parentTimer) {
        this.parent = parentTimer;
        parentTimer.notifyEvery(this, TimingTest.calculateResolution(instructions.get(0).speed, instructions.get(0).perMinute));
    }

    public void addRenderer(Renderer renderer) {
        this.renderers.add(renderer);
    }
}

class ResolutionInstruction implements Instruction{

    private final long start;
    private final long length;
    public final int speed;
    public final int perMinute;

    public ResolutionInstruction(long start, long length, int speed, int perMinute) {
        this.start = start;
        this.length = length;
        this.speed = speed;
        this.perMinute = perMinute;
    }

    public long start() {
        return start;
    }

    public long length() {
        return length;
    }
}

class UserTimer {

    private Renderer childTimer;
    private int notifyEvery;
    final static int resolution = 1000; // In microseconds
    private final PushByHandClock clock;
    long lastBeat = 0;


    public UserTimer(PushByHandClock clock) {
        this.clock = clock;
    }

    public void notifyEvery(Renderer childTimer, int notifyEvery) {
        this.childTimer = childTimer;
        this.notifyEvery = notifyEvery;
    }

    long checkBeat() {
        long lastTime = checkBeat(lastBeat);
        if(lastTime >= lastBeat) {
            lastBeat = lastTime;
            childTimer.notify(lastBeat);
        }
        return lastTime;
    }
    long checkBeat(long lastFoundBeat) {
        long currentTime = clock.getCurrentTimeMillis();
        if(lastFoundBeat + notifyEvery <= currentTime) {
            return checkBeat(lastFoundBeat + notifyEvery);
        } else
            return lastFoundBeat;
    }

    public int resolution() {
        return resolution;
    }

}

class PushByHandClock {

    long currentTimeMillis = 0;

    public long getCurrentTimeMillis() {
        return currentTimeMillis;
    }
}

