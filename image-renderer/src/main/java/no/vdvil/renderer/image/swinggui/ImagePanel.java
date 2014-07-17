package no.vdvil.renderer.image.swinggui;

import no.vdvil.renderer.image.ImageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import no.lau.vdvil.cache.FileRepresentation;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel implements ImageListener {

    private BufferedImage image;
    private static Logger log = LoggerFactory.getLogger(ImagePanel.class);

    public void show(FileRepresentation fileRepresentation) {
        try {
            image = ImageIO.read(fileRepresentation.localStorage());
            if(image == null) {
                log.error("Could not show empty image {}", fileRepresentation.remoteAddress());
            } else {
                setSize(image.getWidth(), image.getHeight());
                repaint();
            }
        } catch (IOException e) {
            log.error("Problem loading image", e);
        }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}