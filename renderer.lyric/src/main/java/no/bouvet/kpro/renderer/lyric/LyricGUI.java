package no.bouvet.kpro.renderer.lyric;

import javax.swing.*;

public class LyricGUI implements LyricListener {
    JFrame frame;
    JLabel label;
    String text = "";

    public void createAndShowGUI() {
        frame = new JFrame("LyricRendererGUITest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label = new JLabel("Hello World");
        frame.getContentPane().add(label);
        //frame.pack();
        frame.setSize(1000, 100);
        frame.setVisible(true);
    }

    public void fire(String text) {
        this.text += " " + text;
        label.setText(this.text);
    }
}
