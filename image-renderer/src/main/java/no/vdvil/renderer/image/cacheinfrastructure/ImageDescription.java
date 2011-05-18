package no.vdvil.renderer.image.cacheinfrastructure;

import no.vdvil.renderer.image.ImageInstruction;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.net.URL;

public class ImageDescription {

    final public URL src;

    private ImageDescription(URL src) {
        this.src = src;
    }

    /**
     * Ugly implemented Parser of "xml" documents to fetch out values which result in an ImageDescription
     * @param inputStream the XML/HTML Document to parse
     * @return an ImageDescription
     * @throws Exception if anything blows up in your face. There will be lots of these :)
     */
    public static ImageDescription parse(InputStream inputStream) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true); // never forget this!
        Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream);

        //String xpathExpression = "//book[author='Neal Stephenson']/title/text()";
        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xpath.compile("//@src");

        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        URL imageSource = new URL(nodes.item(0).getNodeValue());
        return new ImageDescription(imageSource);
    }

    public ImageInstruction asInstruction(int start, int end, float bpm) throws Exception {
        return new ImageInstruction(start, end, bpm, src);
    }
}
