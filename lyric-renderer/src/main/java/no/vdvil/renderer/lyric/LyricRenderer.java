package no.vdvil.renderer.lyric;

import no.bouvet.kpro.renderer.Instruction;
import no.lau.vdvil.handler.CompositionI;
import no.lau.vdvil.renderer.SuperRenderer;
import no.lau.vdvil.timing.MasterBeatPattern;
import no.lau.vdvil.timing.Time;
import javax.swing.*;
import java.io.IOException;

public class LyricRenderer extends SuperRenderer implements LyricListener {
    JFrame frame;
    JLabel label;
    String text = "";
    private Instruction nextInstruction = null;


    public LyricRenderer(int width, int height) {
        frame = new JFrame("LyricRendererGUITest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        label = new JLabel("Lyric Renderer Panel");
        frame.getContentPane().add(label);
        //frame.pack();
        frame.setSize(width, height);
    }

    public void ping(Time time) {
        if (time.asInt() >= nextInstruction._start && nextInstruction instanceof LyricInstruction) {
            System.out.println("Showing text " + nextInstruction + " at " + time);
            shoutHello((LyricInstruction) nextInstruction);
            instructionSet.remove(nextInstruction);
            //Find next Instruction to be rendered
            nextInstruction = findNextInstruction(time.asInt(), instructionSet);
        }
    }


    private void shoutHello(final LyricInstruction lyricInstruction) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                fire(lyricInstruction.text);
            }
        });
    }


    @Override
    public void setComposition(CompositionI composition, MasterBeatPattern beatPattern) throws IOException {
        super.setComposition(composition, beatPattern);
        nextInstruction = findNextInstruction(0, instructionSet);
    }


    @Override
    protected boolean passesFilter(Instruction instruction) {
        return instruction instanceof LyricInstruction;
    }


    @Override
    public void stop(Instruction instruction) {
        instructionSet.remove(instruction);
        if (instructionSet.isEmpty())
            frame.setVisible(false);
    }

    public void fire(String text) {
        this.text += " " + text;
        label.setText(this.text);
        if (!frame.isVisible())
            frame.setVisible(true);
    }
}
