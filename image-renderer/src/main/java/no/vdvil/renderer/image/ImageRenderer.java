package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.instruction.ImageInstruction;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.renderer.Renderer;
import no.vdvil.renderer.image.swinggui.ImagePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;

public class ImageRenderer extends AbstractRenderer implements Renderer {
    private final ImageListener[] listener;
    final List<Instruction> runningImageInstructionList = new ArrayList<>();
    final JFrame frame;
    final Logger log = LoggerFactory.getLogger(getClass());

    public ImageRenderer(int width, int height) {
        listener = new ImageListener[] {new ImagePanel()};
        frame = new JFrame("ImageRendererGUITest");
        frame.setSize(width, height);
        for (ImageListener imageListener : listener) {
            frame.getContentPane().add((JComponent) imageListener);
        }
    }

    public void notify(Instruction instruction, long beat) {
        if (instruction instanceof ImageInstruction) {
            log.info("BPM: {} start:{} actual:{}", instruction, instruction.start(), beat);
            runningImageInstructionList.add(instruction);
            renderStuff(((ImageInstruction) instruction).fileRepresentation());
        }
    }

    private void renderStuff(final FileRepresentation fileRepresentation) {
        for (final ImageListener imageListener : listener) {
            SwingUtilities.invokeLater(() -> {
                try {
                    imageListener.show(fileRepresentation);
                } catch (Exception e) {
                    log.error("Error loading image {}", fileRepresentation, e);
                }
            });
            if (!frame.isVisible())
                frame.setVisible(true);
        }
    }

    public boolean isRendering() {
        return !runningImageInstructionList.isEmpty();
    }

    public void stop(Instruction instruction) {
        runningImageInstructionList.remove(instruction);
        if(runningImageInstructionList.isEmpty())
            frame.setVisible(false);
    }
}
