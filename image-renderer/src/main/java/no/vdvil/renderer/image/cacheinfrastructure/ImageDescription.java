package no.vdvil.renderer.image.cacheinfrastructure;

import no.lau.vdvil.handler.MultimediaPart;
import no.vdvil.renderer.image.ImageInstruction;
import java.io.IOException;
import java.net.URL;

public class ImageDescription implements MultimediaPart{

    final public URL src;
    int start;
    int end;
    //TODO THIS IS CRAAAAZY, need to set start/end correctly!

    public ImageDescription(URL src) {
        this.src = src;
    }

    public ImageInstruction asInstruction(Float masterBpm) throws IOException {
        return new ImageInstruction(start, end, masterBpm, src);
    }
}
