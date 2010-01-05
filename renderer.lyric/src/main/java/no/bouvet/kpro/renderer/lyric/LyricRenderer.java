package no.bouvet.kpro.renderer.simple;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.model.Event;

import org.apache.log4j.Logger;

public class LyricRenderer extends AbstractRenderer
{
    static Logger log = Logger.getLogger(LyricRenderer.class);
    private LyricListener[] listener;

    public LyricRenderer() {
        

    }
    public LyricRenderer(LyricListener... listener) {
        this.listener = listener;
    }

    public void handleInstruction(int time, Instruction instruction) {
        log.debug("time = " + time);
        if (instruction instanceof LyricInstruction) {
            LyricInstruction lyricInstruction = (LyricInstruction) instruction;
            excecuteEvent(lyricInstruction.getEvent());
        }
    }

    private void excecuteEvent(Event event) {
        log.debug("Show: " + event.getName());
        for (LyricListener lyricListener : listener) {
            lyricListener.fire("Show: " + event.getName());
        }
    }
}
