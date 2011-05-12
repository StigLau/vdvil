package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instruction;
import no.vdvil.renderer.image.swinggui.ImagePanel;
import javax.swing.*;
import java.awt.*;

public class ImageRenderer extends AbstractRenderer {
    private ImageListener[] listener;
    JFrame frame;

    public ImageRenderer(int width, int height) {
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
        frame.setVisible(true);
        return true;
    }

    @Override
    public void stop() {
        frame.setVisible(false);
        //frame.dispose();
    }

    public void handleInstruction(int time, Instruction instruction) {
        if (instruction instanceof ImageInstruction) {
            final ImageInstruction imageInstruction = (ImageInstruction) instruction;
            for (final ImageListener imageListener : listener) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        imageListener.show(imageInstruction.imageStream);
                    }
                });
            }
        }
    }

}
