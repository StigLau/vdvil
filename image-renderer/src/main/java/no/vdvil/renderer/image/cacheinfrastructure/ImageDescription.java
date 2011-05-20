package no.vdvil.renderer.image.cacheinfrastructure;

import no.lau.vdvil.handler.MultimediaPart;
import no.vdvil.renderer.image.ImageInstruction;
import java.net.URL;

public class ImageDescription implements MultimediaPart{

    final public URL src;

    public ImageDescription(URL src) {
        this.src = src;
    }
    public ImageInstruction asInstruction(int start, int end, float bpm) throws Exception {
        return new ImageInstruction(start, end, bpm, src);
    }
}
