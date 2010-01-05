package no.bouvet.kpro.renderer.simple;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.SimpleLyricInstruction;

public class LyricRenderer extends AbstractRenderer
{
    private LyricListener[] listener;

    public LyricRenderer(LyricListener... listener) {
        this.listener = listener;
    }

    public void handleInstruction(int time, Instruction instruction) {
        if (instruction instanceof SimpleLyricInstruction) {
            shoutHello((SimpleLyricInstruction) instruction);
        }
    }

    private void shoutHello(final SimpleLyricInstruction lyricInstruction) {
        for (LyricListener lyricListener : listener) {
            if (lyricListener instanceof LyricGUI) {
                final LyricGUI lyricGUI = (LyricGUI) lyricListener;
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        lyricGUI.fire(lyricInstruction.text);
                    }
                });
            }
        }
    }
}
