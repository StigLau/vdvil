package no.vdvil.renderer.image.cacheinfrastructure;

import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.vdvil.renderer.image.ImageInstruction;
import java.io.IOException;
import java.net.URL;

public class ImageDescription implements MultimediaPart{

    public final CompositionInstruction compositionInstruction;
    final public URL src;

    public ImageDescription(CompositionInstruction compositionInstruction, URL src) {
        this.compositionInstruction = compositionInstruction;
        this.src = src;
    }

    public ImageInstruction asInstruction(Float masterBpm) throws IOException {
        return new ImageInstruction(compositionInstruction.start(), compositionInstruction.end(), masterBpm, src);
    }
}
