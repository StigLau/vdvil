package no.vdvil.renderer.image;

import no.lau.tagger.model.AbstractPart;
import no.bouvet.kpro.renderer.Instruction;
import java.net.URL;

/**
 * ImagePart is used for displaying images in context of a swing GUI or equvalent
 *
 * @author Stig@Lau.no
 * @since April 2011
 */
public class ImagePart extends AbstractPart {
    public final URL imageUrl;

    public ImagePart(URL imageUrl, int startCue, int endCue) {
        super(startCue, endCue);
        this.imageUrl = imageUrl;
    }

    @Override
    public Instruction translateToInstruction(Float masterBpm) {
        return new ImageInstruction(startCue, endCue, masterBpm, imageUrl);
    }
}
