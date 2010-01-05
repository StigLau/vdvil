package no.bouvet.kpro.renderer.simple;

import no.lau.tagger.model.Part;
import no.lau.tagger.model.SimpleSong;
import no.bouvet.kpro.renderer.Instruction;

/**
 * LyricPart is a part concerned with lyrics that are to be shown to the user
 * At the moment, it is just an ugly hack :)
 *
 * Will eventually contain
 * text - what is to be displayed
 *
 * @author Stig@Lau.no
 * @since January 2010
 */
public class LyricPart extends Part {
    public final String text;

    public LyricPart(String text, Float startCue, Float endCue) {
        super(new SimpleSong(null, null, null, -0F), startCue, endCue, null);
        this.text = text;
    }

    @Override
    public Instruction translateToInstruction(Float masterBpm) {
        return new SimpleLyricInstruction(startCue, endCue, bpm, text);
    }
}
