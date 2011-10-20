package no.vdvil.renderer.image.cacheinfrastructure;

import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.MultimediaPart;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import no.vdvil.renderer.image.ImageInstruction;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageDescription implements MultimediaPart{

    public final CompositionInstruction compositionInstruction;
    final public URL src;
    private InputStream cachedImage;

    public ImageDescription(CompositionInstruction compositionInstruction, URL src) {
        this.compositionInstruction = compositionInstruction;
        this.src = src;
    }

    public ImageInstruction asInstruction(Float masterBpm) {
        return ImageInstruction.create(compositionInstruction.start(), compositionInstruction.end(), masterBpm, src, cachedImage);
    }

    public CompositionInstruction compositionInstruction() {
        return compositionInstruction;
    }

    public void cache(DownloadAndParseFacade downloader) throws IOException {
        cachedImage = downloader.fetchAsStream(src);
    }
}
