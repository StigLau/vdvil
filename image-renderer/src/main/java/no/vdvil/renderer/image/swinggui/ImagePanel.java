package no.vdvil.renderer.image.swinggui;

import no.vdvil.renderer.image.ImageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel implements ImageListener {

    private BufferedImage image;
    private static Logger log = LoggerFactory.getLogger(ImagePanel.class);

    public void show(InputStream imageStream) {
        try {
            image = ImageIO.read(imageStream);
            setSize(image.getWidth(), image.getHeight());
            repaint();
        } catch (IOException e) {
            log.error("Problem loading image", e);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}