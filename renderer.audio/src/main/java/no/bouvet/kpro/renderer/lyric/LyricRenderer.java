package no.bouvet.kpro.renderer.lyric;

import no.bouvet.kpro.renderer.AbstractRenderer;
import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.model.stigstest.Event;

import org.apache.log4j.Logger;

public class LyricRenderer extends AbstractRenderer
{
    static Logger log = Logger.getLogger(LyricRenderer.class);

    

    public void handleInstruction(int time, Instruction instruction) {
        System.out.println("time = " + time);
        if (instruction instanceof LyricInstruction) {
            LyricInstruction lyricInstruction = (LyricInstruction) instruction;
            excecuteEvent(lyricInstruction.getEvent());
        }
    }

    private void excecuteEvent(Event event) {
        System.out.println("Show: " + event.getName());
    }
}
