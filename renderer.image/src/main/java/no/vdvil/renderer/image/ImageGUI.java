package no.vdvil.renderer.image;

import no.vdvil.renderer.image.ImageListener;
import javax.swing.*;

public class ImageGUI implements ImageListener {
    JFrame frame;
    JLabel label;
    String text = "";

    public void create() {
        frame = new JFrame("LyricRendererGUITest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label = new JLabel("Hello World");
        frame.getContentPane().add(label);
        //frame.pack();
        frame.setSize(1000, 100);
    }
    public void show() {
        frame.setVisible(true);
    }

    public void fire(String text) {
        this.text += " " + text;
        label.setText(this.text);
    }
}
