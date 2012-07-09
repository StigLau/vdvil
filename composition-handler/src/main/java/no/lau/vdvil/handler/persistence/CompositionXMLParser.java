package no.lau.vdvil.handler.persistence;

import com.thoughtworks.xstream.XStream;
import no.lau.vdvil.handler.DownloadAndParseFacade;
import no.lau.vdvil.handler.Composition;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.persistence.domain.CompositionXml;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CompositionXMLParser implements MultimediaParser{

    XStream xstream;
    DownloadAndParseFacade downloadAndParseFacade;

    public CompositionXMLParser(DownloadAndParseFacade facade) {
        downloadAndParseFacade = facade;
        xstream = new XStream();
        xstream.alias("composition", CompositionXml.class);
        xstream.alias("part", PartXML.class);
        xstream.alias("dvl", DvlXML.class);
    }

    @Deprecated // Could instead use parse(URL)
    public Composition parse(CompositionInstruction compositionInstruction) throws IOException {
        URL url = compositionInstruction.dvl().url();
        CompositionXml compositionXml = parseCompositionXml(downloadAndParseFacade.fetchAsStream(url));
        return compositionXml.asComposition(url, downloadAndParseFacade);
    }

    public CompositionXml parseCompositionXml(InputStream stream) throws IOException {
        return (CompositionXml) xstream.fromXML(stream);
    }
}
