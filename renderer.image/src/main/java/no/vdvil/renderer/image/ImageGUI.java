package no.vdvil.renderer.image;

import no.vdvil.renderer.image.ImageListener;
import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageGUI {
    JFrame frame;
    public final ImagePanel imagePanel = new ImagePanel();

    public ImageGUI(int width, int height) {
        frame = new JFrame("ImageRendererGUITest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.getContentPane().add(imagePanel);
        frame.setVisible(true);
    }
}
