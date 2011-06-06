package no.vdvil.renderer.image;

import no.bouvet.kpro.renderer.Instruction;
import no.bouvet.kpro.renderer.Renderer;
import no.lau.vdvil.cache.DownloaderFacade;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class ImageInstruction extends Instruction {
    public final URL imageUrl;
    public InputStream cachedStream = null;

    public ImageInstruction(int start, int end, float bpm, final URL imageUrl) {
        this.imageUrl = imageUrl;
        float speedFactor = Renderer.RATE * 60 / bpm;

        _start = new Float(start * speedFactor).intValue() ;
        _end = new Float(end * speedFactor).intValue();
    }

    public void cache(DownloaderFacade cache) throws IOException {
        cachedStream = cache.fetchAsStream(imageUrl);
    }
}
