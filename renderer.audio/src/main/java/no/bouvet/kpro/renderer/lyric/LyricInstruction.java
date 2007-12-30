package no.bouvet.kpro.renderer.lyric;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.model.stigstest.Event;

/**
 * Implementation to show on-screen lyrics.
 * An example for testing the renderer
 *
 * @author Stig Lau
 */
public class LyricInstruction extends Instruction {
    private Event event;
    private Double bpm;
    private int minute = 60 * 1000;

    public LyricInstruction(Event event) {
        this.event = event;
        bpm = event.getBPM();
        super._start = getFactoredValue(event.getStartTime());
        super._end = getFactoredValue(event.getStartTime() + event.getLength());
    }

    private int getFactoredValue(Double beats) {
        //TODO should this be improved?
        return (int) ((beats * minute) / bpm);
    }

    public Event getEvent() {
        return event;
    }

    public int getSourceDuration() {
        return event.getLength().intValue();
    }
}
