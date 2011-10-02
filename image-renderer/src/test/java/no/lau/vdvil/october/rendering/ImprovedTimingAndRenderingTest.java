package no.lau.vdvil.october.rendering;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instruction;
import no.vdvil.renderer.image.ImageInstruction;
import org.junit.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImprovedTimingAndRenderingTest {
    @Test
    public void testTiming() throws MalformedURLException, InterruptedException {
        ImprovedRenderer renderer = new ImprovedRenderer();
        renderer.handleInstruction(-1, new ImageInstruction(0, 8, 120, new URL("http://www.1.no"), null));
        renderer.handleInstruction(-1, new ImageInstruction(1, 8, 120, new URL("http://www.1.no"), null));
        /*
        renderer.handleInstruction(-1, new ImageInstruction(2, 8, 120, new URL("http://www.1.no"), null));
        renderer.handleInstruction(-1, new ImageInstruction(3, 8, 120, new URL("http://www.1.no"), null));
        renderer.handleInstruction(-1, new ImageInstruction(4, 8, 120, new URL("http://www.2.no"), null));
        renderer.handleInstruction(-1, new ImageInstruction(5, 8, 120, new URL("http://www.2.no"), null));
        renderer.handleInstruction(-1, new ImageInstruction(6, 8, 120, new URL("http://www.2.no"), null));
        renderer.handleInstruction(-1, new ImageInstruction(7, 8, 120, new URL("http://www.2.no"), null));
        renderer.handleInstruction(-1, new ImageInstruction(8, 8, 120, new URL("http://www.3.no"), null));
        renderer.handleInstruction(-1, new ImageInstruction(12, 8, 120, new URL("http://www.3.no"), null));
        renderer.handleInstruction(-1, new ImageInstruction(16, 8, 120, new URL("http://www.4.no"), null));
        */
        System.out.println("--------------");
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
        System.out.println("Adding " + instruction.toString() + " at " + instruction.getStart());
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
    public boolean isRunning() {
        return isRunning;
    }
}

class Timer implements Runnable {

    long startTime = System.currentTimeMillis();
    List<TimingRenderer> renderers = new ArrayList<TimingRenderer>();
    boolean isRunning = true;

    @Override
    public void run() {

        while(true) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            long convertedElapsedTime =  4410 * elapsedTime/1000 * 8;
            try {
                for (int i = 0; i < renderers.size(); i++) {
                    TimingRenderer renderer = renderers.get(i);
                    if(renderer.isRunning()) {
                        renderer.ping(convertedElapsedTime);
                    } else {
                        renderers.remove(renderer);
                        if (renderers.isEmpty()) {
                            isRunning = false;
                            System.out.println("Isn't running any more");
                        }
                    }
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
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