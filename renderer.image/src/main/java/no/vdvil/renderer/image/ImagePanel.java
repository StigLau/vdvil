package no.vdvil.renderer.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel implements ImageListener {

    private BufferedImage image;

    public void show(URL imageURL) {
        try {
            image = ImageIO.read(imageURL);
            setSize(image.getWidth(), image.getHeight());
            repaint();
        } catch (IOException e) {
            //TODO Use LOGGING FRAMEWORK!!!
            System.out.println("Problem loading " + imageURL + " - " + e.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters
    }
}