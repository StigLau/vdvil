package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instruction;

public class ImageRenderer extends AbstractRenderer
{
    private ImageListener[] listener;

    public ImageRenderer(ImageListener... listener) {
        this.listener = listener;
    }

    public void handleInstruction(int time, Instruction instruction) {
        if (instruction instanceof ImageInstruction) {
            shoutHello((ImageInstruction) instruction);
        }
    }

    private void shoutHello(final ImageInstruction imageInstruction) {
        for (ImageListener imageListener : listener) {
            if (imageListener instanceof ImageGUI) {
                final ImageGUI lyricGUI = (ImageGUI) imageListener;
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        lyricGUI.fire(imageInstruction.url);
                    }
                });
            }
        }
    }
}
