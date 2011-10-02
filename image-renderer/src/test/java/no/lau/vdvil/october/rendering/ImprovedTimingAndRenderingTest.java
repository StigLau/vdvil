package no.lau.vdvil.october.rendering;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instruction;
import no.vdvil.renderer.image.ImageInstruction;
import org.junit.Test;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ImprovedTimingAndRenderingTest {
    @Test
    public void testTiming() throws MalformedURLException, InterruptedException {
        ImprovedRenderer renderer = new ImprovedRenderer();
        renderer.handleInstruction(-1, new ImageInstruction(0, -1, 120, null, null));
        renderer.handleInstruction(-1, new ImageInstruction(1, -1, 120, null, null));
        /*
        renderer.handleInstruction(-1, new ImageInstruction(2, -1, 120, null, null));
        renderer.handleInstruction(-1, new ImageInstruction(3, -1, 120, null, null));
        renderer.handleInstruction(-1, new ImageInstruction(4, -1, 120, null, null));
        renderer.handleInstruction(-1, new ImageInstruction(5, -1, 120, null, null));
        renderer.handleInstruction(-1, new ImageInstruction(6, -1, 120, null, null));
        renderer.handleInstruction(-1, new ImageInstruction(7, -1, 120, null, null));
        renderer.handleInstruction(-1, new ImageInstruction(8, -1, 120, null, null));
        renderer.handleInstruction(-1, new ImageInstruction(12, -1, 120, null, null));
        renderer.handleInstruction(-1, new ImageInstruction(16, -1, 120, null, null));
        */
        Timer myTimer = new Timer();
        myTimer.addRenderer(renderer);
        new Thread(myTimer).start();
        while(myTimer.isRunning()) {
            Thread.sleep(1000);
        }
        System.out.println("Timer finished rendering");
    }
}

class ImprovedRenderer extends AbstractRenderer implements TimingRenderer {

    List<Instruction> instructions = new ArrayList<Instruction>();
    boolean isRunning = true;

    @Override
    public void handleInstruction(int time, Instruction instruction) {
        instructions.add(instruction);
    }

    @Override
    public void ping(long elapsedTime) {
        if (!instructions.isEmpty()) {
            Instruction instruction = instructions.get(0);
            //System.out.println("At " + elapsedTime + " Instruction is starting in " + (instruction.getStart() - elapsedTime));
            if (elapsedTime > instruction.getStart()) {
                instructions.remove(0); //Removing the used instruction
                System.out.println("Playing " + instruction);
                //Calling this again in case another has the same executionTime
                ping(elapsedTime);
            }
        } else {
            System.out.println(this + " halted");
            isRunning = false;
        }
    }

    @Override
    public boolean isRunning() { return isRunning; }
}

class Timer implements Runnable {

    long startTime = System.currentTimeMillis();
    List<TimingRenderer> renderers = new ArrayList<TimingRenderer>();
    boolean isRunning = true;

    @Override
    public void run() {
        while(true) {
            handleStuff();
        }
    }

    private void handleStuff() {
        long elapsedTime = calculateCurrentTime();
        try {
            for (int i = 0; i < renderers.size(); i++) {
                TimingRenderer renderer = renderers.get(i);
                if (renderer.isRunning()) {
                    renderer.ping(elapsedTime);
                } else {
                    renderers.remove(renderer);
                    if (renderers.isEmpty()) {
                        isRunning = false;
                        System.out.println(this + " halted");
                    }
                }
            }
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException("WTF!?! " + e);
        }
    }

    private long calculateCurrentTime() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        return 4410 * elapsedTime / 1000 * 8;
    }

    public void addRenderer(TimingRenderer renderer) {
        this.renderers.add(renderer);
    }

    public boolean isRunning() {
        return isRunning;
    }
}

interface TimingRenderer {
    void ping(long elapsedTime);
    boolean isRunning();
}