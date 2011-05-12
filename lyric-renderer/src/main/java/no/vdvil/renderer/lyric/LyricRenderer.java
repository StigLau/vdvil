package no.vdvil.renderer.lyric;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instruction;

import javax.swing.*;

public class LyricRenderer extends AbstractRenderer implements LyricListener{
    JFrame frame;
    JLabel label;
    String text = "";


    public LyricRenderer(int width, int height) {
        frame = new JFrame("LyricRendererGUITest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label = new JLabel("Hello World");
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
        frame.setVisible(true);
        return true;
    }

    @Override
    public void stop() {
        frame.setVisible(false);
        //frame.dispose();
    }

    public void fire(String text) {
        this.text += " " + text;
        label.setText(this.text);
    }
}
