package no.vdvil.renderer.image;

import no.lau.tagger.model.AbstractPart;
import no.bouvet.kpro.renderer.Instruction;

/**
 * ImagePart is a part concerned with lyrics that are to be shown to the user
 * At the moment, it is just an ugly hack :)
 *
 * Will eventually contain
 * url - what is to be displayed
 *
 * @author Stig@Lau.no
 * @since January 2010
 */
public class ImagePart extends AbstractPart {
    public final String url;

    public ImagePart(String url, int startCue, int endCue) {
        super(startCue, endCue);
        this.url = url;
    }

    @Override
    public Instruction translateToInstruction(Float masterBpm) {
        return new ImageInstruction(startCue, endCue, masterBpm, url);
    }
}
