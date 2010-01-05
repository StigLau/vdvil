package no.bouvet.kpro.renderer.simple;

import no.lau.tagger.model.AbstractPart;
import no.lau.tagger.model.IPart;
import no.bouvet.kpro.renderer.Instruction;
import java.util.List;
import java.util.Collections;

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
public class LyricPart extends AbstractPart {
    public final String text;

    public LyricPart(String text, Float startCue, Float endCue) {
        super(startCue, endCue);
        this.text = text;
    }

    @Override
    public Instruction translateToInstruction(Float masterBpm) {
        return new SimpleLyricInstruction(startCue, endCue, masterBpm, text);
    }

    public List<? extends IPart> children() {
        return Collections.emptyList();
    }
}
