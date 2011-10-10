package no.vdvil.renderer.lyric;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instruction;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LyricRenderer extends AbstractRenderer implements LyricListener{
    JFrame frame;
    JLabel label;
    String text = "";
    List<LyricInstruction> runningLyricInstructions = new ArrayList<LyricInstruction>();


    public LyricRenderer(int width, int height) {
        frame = new JFrame("LyricRendererGUITest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label = new JLabel("Lyric Renderer Panel");
        frame.getContentPane().add(label);
        //frame.pack();
        frame.setSize(width, height);
    }

    public void handleInstruction(int time, Instruction instruction) {
        if (instruction instanceof LyricInstruction) {
            shoutHello((LyricInstruction) instruction);
        }
    }

    private void shoutHello(final LyricInstruction lyricInstruction) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                fire(lyricInstruction.text);
            }
        });
    }

    @Override
    public boolean start(int time) {
        return true;
    }

    public boolean isRendering() {
        return !runningLyricInstructions.isEmpty();
    }

    @Override
    public void stop(Instruction instruction) {
        runningLyricInstructions.remove(instruction);
                if(runningLyricInstructions.isEmpty())
                            frame.setVisible(false);
    }

    public void fire(String text) {
        this.text += " " + text;
        label.setText(this.text);
        if(!frame.isVisible())
                frame.setVisible(true);
    }
}
