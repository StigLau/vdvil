package no.vdvil.renderer.image.cacheinfrastructure;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class OnlyTheImageDescriptionParser implements MultimediaParser {

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
        FileRepresentation fileRepresentation = Store.get().cache(imageUrl);
        //Try to load as Image
        if(ImageIO.read(fileRepresentation.localStorage()) != null)
            return new ImageDescription(compositionInstruction, fileRepresentation);
        else
            throw new IOException("Could not parse as image: " + compositionInstruction.dvl().name());
    }

    public String toString() {
        return getClass().getName();
    }
}
