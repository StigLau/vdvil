package no.vdvil.renderer.image.cacheinfrastructure;

import no.lau.vdvil.cache.FileRepresentation;
import no.lau.vdvil.cache.Store;
import no.lau.vdvil.handler.MultimediaParser;
import no.lau.vdvil.handler.persistence.CompositionInstruction;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.IOException;

public class ImageDescriptionXMLParser implements MultimediaParser{
    private Store store = Store.get();

    /**
     * Ugly implemented Parser of "xml" documents to fetch out values which result in an ImageDescription
     *
     * @param compositionInstruction representation of the XML/HTML Document to parse
     * @return an ImageDescription
     * @throws IOException if anything blows up in your face. There will be lots of these :)
     */
    public ImageDescription parse(CompositionInstruction compositionInstruction) throws IOException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true); // never forget this!
        try {
            FileRepresentation dvl = store.cache(compositionInstruction.dvl().url());
            Document doc = documentBuilderFactory.newDocumentBuilder().parse(dvl.localStorage().openStream());

            //String xpathExpression = "//book[author='Neal Stephenson']/title/text()";
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xpath.compile("//@src");

            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            FileRepresentation imageFileRepresentation = store.createKey(nodes.item(0).getNodeValue());
            return new ImageDescription(compositionInstruction, imageFileRepresentation);
        } catch (Exception e) {
            throw new IOException("Unable to parse", e);
        }
    }

    public String toString() {
        return getClass().getName();
    }
}
