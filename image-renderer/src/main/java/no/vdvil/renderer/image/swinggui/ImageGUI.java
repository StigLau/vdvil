package no.vdvil.renderer.image.swinggui;

import no.vdvil.renderer.image.ImageListener;

import javax.swing.*;

public class ImageGUI {
    JFrame frame;
    final ImagePanel imagePanel = new ImagePanel();

    public ImageGUI(int width, int height) {
        frame = new JFrame("ImageRendererGUITest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.getContentPane().add(imagePanel);
        frame.setVisible(true);
    }

    public ImageListener getImageListener() {
        return imagePanel;
    }
}
