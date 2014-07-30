package no.vdvil.renderer.lyric;

import no.lau.vdvil.instruction.Instruction;
import no.lau.vdvil.instruction.LyricInstruction;
import no.lau.vdvil.renderer.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.ArrayList;
import java.util.List;

public class LyricRenderer implements LyricListener, Renderer {
    JFrame frame;
    JLabel label;
    String text = "";
    List<LyricInstruction> runningLyricInstructions = new ArrayList<LyricInstruction>();
    Logger log = LoggerFactory.getLogger(LyricRenderer.class);


    public LyricRenderer(int width, int height) {
        frame = new JFrame("LyricRendererGUITest");
        label = new JLabel("Lyric OldRenderer Panel");
        frame.getContentPane().add(label);
        //frame.pack();
        frame.setSize(width, height);
    }

    private void shoutHello(final LyricInstruction lyricInstruction) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                fire(lyricInstruction.text);
            }
        });
    }

    public boolean isRendering() {
        return !runningLyricInstructions.isEmpty();
    }

    public void stop(Instruction instruction) {
        runningLyricInstructions.remove(instruction);
                if(runningLyricInstructions.isEmpty())
                            frame.setVisible(false);
    }

    public void fire(String text) {
        this.text += " " + text;
        label.setText(this.text);
        if(!frame.isVisible())
                frame.setVisible(true);
    }

    public void notify(Instruction instruction, long beat) {
        log.info("{} to be played at {}",instruction.getClass().getSimpleName(), beat);
        shoutHello((LyricInstruction) instruction);
    }
}
