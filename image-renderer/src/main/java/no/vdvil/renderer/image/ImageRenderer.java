package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instruction;
import no.lau.vdvil.cache.DownloaderFacade;
import no.vdvil.renderer.image.swinggui.ImagePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class ImageRenderer extends AbstractRenderer {
    private ImageListener[] listener;
    JFrame frame;
    private DownloaderFacade cache;
    Logger log = LoggerFactory.getLogger(getClass());

    public ImageRenderer(int width, int height, DownloaderFacade cache) {
        this.cache = cache;
        listener = new ImageListener[] {new ImagePanel()};

        frame = new JFrame("ImageRendererGUITest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        for (ImageListener imageListener : listener) {
            frame.getContentPane().add((Component) imageListener);
        }
    }

    @Override
    public boolean start(int time) {
        return true;
    }

    @Override
    public void stop() {
        frame.setVisible(false);
    }

    public void handleInstruction(int time, Instruction instruction) {
        log.debug("Got instruction {} to be played at {}", instruction, time);
        if (instruction instanceof ImageInstruction) {
            final ImageInstruction imageInstruction = (ImageInstruction) instruction;
            for (final ImageListener imageListener : listener) {
                try {
                    final InputStream imageStream = cache.fetchAsStream(imageInstruction.imageUrl);
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        imageListener.show(imageStream);
                    }
                });
                    if(!frame.isVisible())
                        frame.setVisible(true);
                    
                } catch (IOException e) {
                    log.error("Error loading image {}", imageInstruction.imageUrl, e);
                }
            }
        }
    }
}
