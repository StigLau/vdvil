package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.lau.vdvil.cache.DownloaderFacade;
import no.lau.vdvil.instruction.ImageInstruction;
import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.renderer.Renderer;
import no.vdvil.renderer.image.swinggui.ImagePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.JFrame;
import javax.swing.JComponent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ImageRenderer extends AbstractRenderer implements Renderer {
    private ImageListener[] listener;
    List<no.lau.vdvil.instruction.Instruction> runningImageInstructionList = new ArrayList<no.lau.vdvil.instruction.Instruction>();
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
            frame.getContentPane().add((JComponent) imageListener);
        }
    }

    @Override
    public boolean start(int time) {
        return true;
    }

    public void notify(Instruction instruction, long beat) {
        if (instruction instanceof ImageInstruction) {
            runningImageInstructionList.add(instruction);
            renderStuff(((ImageInstruction) instruction).imageUrl);
        }
    }

    private void renderStuff(URL imageUrl) {
        for (final ImageListener imageListener : listener) {
            try {
                final InputStream imageStream = cache.fetchAsStream(imageUrl);
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    imageListener.show(imageStream);
                }
            });
                if(!frame.isVisible())
                    frame.setVisible(true);
            } catch (IOException e) {
                log.error("Error loading image {}", imageUrl, e);
            }
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
