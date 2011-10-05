package no.vdvil.renderer.image.cacheinfrastructure;

import no.lau.vdvil.cache.DownloaderFacade;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class OnlyTheImageDescriptionParser implements MultimediaParser {
    private DownloaderFacade downloaderFacade;

    public OnlyTheImageDescriptionParser(DownloaderFacade downloaderFacade){
        this.downloaderFacade = downloaderFacade;
    }

    /**
     * Ugly implemented Parser of "xml" documents to fetch out values which result in an ImageDescription
     *
     * @param compositionInstruction representation of the XML/HTML Document to parse
     * @return an ImageDescription
     * @throws IOException if anything blows up in your face. There will be lots of these :)
     */
    public ImageDescription parse(CompositionInstruction compositionInstruction) throws IOException {
        URL imageUrl = compositionInstruction.dvl().url();
        //Buffer
        InputStream inputStream = downloaderFacade.fetchAsStream(imageUrl);
        //Try to load as Image
        if(ImageIO.read(inputStream) != null)
            return new ImageDescription(compositionInstruction, imageUrl);
        else
            throw new IOException("Could not parse as image: " + imageUrl);
    }

    public String toString() {
        return getClass().getName();
    }
}
