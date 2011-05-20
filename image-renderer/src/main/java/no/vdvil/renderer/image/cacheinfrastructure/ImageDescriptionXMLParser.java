package no.vdvil.renderer.image.cacheinfrastructure;

import no.lau.vdvil.cache.DownloaderFacade;
import no.lau.vdvil.handler.MultimediaParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageDescriptionXMLParser implements MultimediaParser{
    private DownloaderFacade downloaderFacade;

    public ImageDescriptionXMLParser(){}

    public ImageDescriptionXMLParser(DownloaderFacade downloaderFacade){
        this.downloaderFacade = downloaderFacade;
    }

    /**
     * Ugly implemented Parser of "xml" documents to fetch out values which result in an ImageDescription
     *
     * @param url the XML/HTML Document to parse
     * @return an ImageDescription
     * @throws Exception if anything blows up in your face. There will be lots of these :)
     */
    public ImageDescription parse(URL url) throws IOException{
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true); // never forget this!
        try {
            InputStream inputStream = downloaderFacade.fetchAsStream(url);
            Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream);

            //String xpathExpression = "//book[author='Neal Stephenson']/title/text()";
            XPath xpath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xpath.compile("//@src");

            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

            URL imageSource = new URL(nodes.item(0).getNodeValue());
            return new ImageDescription(imageSource);
        } catch (Exception e) {
            throw new IOException("Unable to parse", e);
        }
    }

    public void setDownloaderFacade(DownloaderFacade downloaderFacade) {
        this.downloaderFacade = downloaderFacade;
    }

    public String toString() {
        return getClass().getName();
    }
}
