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
    public final URL url;

    public ImagePart(URL url, int startCue, int endCue) {
        super(startCue, endCue);
        this.url = url;
    }

    @Override
    public Instruction translateToInstruction(Float masterBpm) {
        return new ImageInstruction(startCue, endCue, masterBpm, url);
    }
}
