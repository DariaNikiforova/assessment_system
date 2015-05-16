package spbstu.iitu.kit.diplom.tomita;

import com.sun.org.apache.xerces.internal.dom.DeferredElementImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides methods for working with xml files.
 * @author Daria Nikiforova
 */
public class XmlParser {

    private final static String ADJTAG = "AdjTag";
    private final static String NOUNTAG = "NounTag";
    private final static String VERBTAG = "VerbTag";
    private final static String OTHERTAG = "OtherTag";

    /**
     * Private default constructor.
     */
    private XmlParser() {}

    private static Document parseXmlFile(String filename) {
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document dom = null;
        try {
            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            //parse using builder to get DOM representation of the XML file
            dom = db.parse(filename);
        } catch(ParserConfigurationException | SAXException | IOException pce) {
            pce.printStackTrace();
        }
        return dom;
    }

    public static Map<String, String> getAnswerPartsList(String filename){
        //get the root element
        Document dom = parseXmlFile(filename);
        Element docElement = dom.getDocumentElement();

        Map<String, String> resultMap = new HashMap<>();
        resultMap.putAll(getWordsMapByPartOfSpeechTag(docElement, ADJTAG));
        resultMap.putAll(getWordsMapByPartOfSpeechTag(docElement, NOUNTAG));
        resultMap.putAll(getWordsMapByPartOfSpeechTag(docElement, VERBTAG));
        resultMap.putAll(getWordsMapByPartOfSpeechTag(docElement, OTHERTAG));

        return resultMap;
    }

    private static Map<String, String> getWordsMapByPartOfSpeechTag(Element documentElement, String partOfSpeechTag) {
        NodeList nodes = documentElement.getElementsByTagName(partOfSpeechTag);
        Map<String, String> partOfSpeechWords = new HashMap<>();
        if(nodes != null && nodes.getLength() > 0) {
            for(int i = 0 ; i < nodes.getLength();i++) {
                //get the employee element
                Element element = (Element)nodes.item(i);
                //get the Employee object
                Node valueNode = element.getFirstChild();
                if(valueNode != null) {
                    partOfSpeechWords.put(((DeferredElementImpl) valueNode).getAttribute("val"), partOfSpeechTag);
                }
            }
        }
        return partOfSpeechWords;
    }
}
