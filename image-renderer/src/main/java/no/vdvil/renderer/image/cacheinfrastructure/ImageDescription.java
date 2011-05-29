package no.vdvil.renderer.image.cacheinfrastructure;

import no.lau.vdvil.handler.MultimediaPart;
import no.vdvil.renderer.image.ImageInstruction;
import java.io.IOException;
import java.net.URL;

public class ImageDescription implements MultimediaPart{

    final public URL src;

    public ImageDescription(URL src) {
        this.src = src;
    }

    public ImageInstruction asInstruction(int cue, int end, Float masterBpm) throws IOException {
        return new ImageInstruction(cue, end, masterBpm, src);
    }
}
