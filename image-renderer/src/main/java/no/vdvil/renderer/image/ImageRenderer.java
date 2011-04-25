package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instruction;

public class ImageRenderer extends AbstractRenderer {
    private ImageListener[] listener;

    public ImageRenderer(ImageListener... listener) {
        this.listener = listener;
    }

    public void handleInstruction(int time, Instruction instruction) {
        if (instruction instanceof ImageInstruction) {
            invokeListener((ImageInstruction) instruction);
        }
    }

    private void invokeListener(final ImageInstruction imageInstruction) {
        for (final ImageListener imageListener : listener) {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    imageListener.show(imageInstruction.imageStream);
                }
            });
        }
    }
}
