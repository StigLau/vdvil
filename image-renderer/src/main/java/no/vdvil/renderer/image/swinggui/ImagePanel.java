package no.vdvil.renderer.image.swinggui;

import no.vdvil.renderer.image.ImageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import no.lau.vdvil.cache.FileRepresentation;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Dimension;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel implements ImageListener {

    private Image image;
    private static Logger log = LoggerFactory.getLogger(ImagePanel.class);

    public void show(FileRepresentation fileRepresentation) {
        try {
            BufferedImage tmpImage = ImageIO.read(fileRepresentation.localStorage());
            if(tmpImage != null) {
                log.trace("Resizes {} to fit screen", fileRepresentation.remoteAddress());
                image = resizeImageToFrameSize();
                repaint();
            }
        } catch (IOException e) {
            log.error("Problem loading image", e);
        }
    }

    public Image resizeImageToFrameSize() {
        Dimension recalculated = recalculateImageSize(image.getWidth(null), image.getHeight(null), this.getWidth(), this.getHeight());
        return image.getScaledInstance(recalculated.width, recalculated.height, Image.SCALE_DEFAULT);
    }

    public Dimension recalculateImageSize(int imgWidth, int imgHeight, final int panelWidth, final int panelHeight) {
        if(panelWidth == 0|| panelHeight == 0) {
            log.error("warn for panel divide by zero width {} heigth {}", panelWidth, panelHeight);
        }
        else {
            if (panelWidth > 0 && imgWidth > panelWidth) {
                int newRatio = imgWidth / panelWidth;
                imgWidth = panelWidth;
                imgHeight = imgHeight / newRatio;
            }
            if (panelHeight > 0 && imgHeight > panelHeight) {
                int newRatio = imgHeight / panelHeight;
                imgHeight = panelHeight;
                imgWidth = imgWidth / newRatio;
            }
        }
        return new Dimension(imgWidth, imgHeight);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }
}