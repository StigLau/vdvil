package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.Instruction;
import no.lau.vdvil.cache.DownloaderFacade;
import no.lau.vdvil.handler.CompositionI;
import no.lau.vdvil.renderer.SuperRenderer;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.lau.vdvil.timing.Time;
import no.vdvil.renderer.image.swinggui.ImagePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.*;
import java.awt.Component;
import java.io.IOException;
import java.io.InputStream;

public class ImageRenderer extends SuperRenderer {
    private ImageListener[] listener;
    JFrame frame;
    private DownloaderFacade cache;
    Logger log = LoggerFactory.getLogger(getClass());
    public static Instruction START = new Instruction(Integer.MIN_VALUE, Integer.MIN_VALUE);
    private Instruction currentInstruction = null;
    private Instruction nextInstruction = START;

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
    public void setComposition(CompositionI composition, MasterBeatPattern beatPattern) {
        super.setComposition(composition, beatPattern);
        nextInstruction = findNextInstruction(0, instructionSet);
    }


    public void ping(Time time) {
        if (nextInstruction != currentInstruction && time.asInt() >= nextInstruction._start && nextInstruction instanceof ImageInstruction) {
            System.out.println("Showing image " + nextInstruction + " at " + time);
            render((ImageInstruction) nextInstruction);
            instructionSet.remove(nextInstruction);
            //Find next Image to be rendered
            nextInstruction = findNextInstruction(time.asInt(), instructionSet);
        }
    }

    private void render(ImageInstruction imageInstruction) {
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

    public void stop(Instruction instruction) {
        instructionSet.remove(instruction);
        if(instructionSet.isEmpty())
            frame.setVisible(false);
    }

    @Override
    protected boolean passesFilter(Instruction instruction) {
        return instruction instanceof ImageInstruction;
    }
}
