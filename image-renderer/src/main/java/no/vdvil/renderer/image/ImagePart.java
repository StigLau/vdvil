package no.vdvil.renderer.image;

import no.lau.tagger.model.AbstractPart;
import no.bouvet.kpro.renderer.Instruction;

import java.io.InputStream;

/**
 * ImagePart is used for displaying images in context of a swing GUI or equvalent
 *
 * @author Stig@Lau.no
 * @since April 2011
 */
public class ImagePart extends AbstractPart {
    public final InputStream imageStream;

    public ImagePart(InputStream imageStream, int startCue, int endCue) {
        super(startCue, endCue);
        this.imageStream = imageStream;
    }

    @Override
    public Instruction translateToInstruction(Float masterBpm) {
        return new ImageInstruction(startCue, endCue, masterBpm, imageStream);
    }
}
