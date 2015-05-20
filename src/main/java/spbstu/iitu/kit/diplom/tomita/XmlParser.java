
package spbstu.iitu.kit.diplom.tomita;

import com.sun.org.apache.xerces.internal.dom.DeferredElementImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import spbstu.iitu.kit.diplom.tomita.dto.Lead;
import spbstu.iitu.kit.diplom.tomita.dto.Fact;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for working with xml files.
 * @author Daria Nikiforova
 */
public class XmlParser {

    private final static String ADJTAG = "AdjTag";
    private final static String NOUNTAG = "NounTag";
    private final static String VERBTAG = "VerbTag";
    private final static String OTHERTAG = "OtherTag";
    private final static String PARTTAG = "PartTag";
    private final static String PARTICIPLETAG = "ParticipleTag";
    private final static String RELATIONTAG = "RelationTag";
    private final static String SUBJECTTAG = "SubjectTag";
    private final static String OBJECTTAG = "ObjectTag";
    private final static String NAMETAG = "NameTag";
    private final static String PRIVILEGETAG = "PrivilegeTag";


    /**
     * Private default constructor.
     */
    private XmlParser() {}

    public static Lead getAnswer(String fileName) {
        Document dom = parseXmlFile(fileName);
        Element docElement = dom.getDocumentElement();

        Lead answer = new Lead();
        answer.setAdjectives(getFacts(docElement, ADJTAG));
        answer.setNouns(getFacts(docElement, NOUNTAG));
        answer.setVerbs(getFacts(docElement, VERBTAG));
        answer.setOthers(getFacts(docElement, OTHERTAG));
        answer.setParts(getFacts(docElement, PARTTAG));
        answer.setParticiples(getFacts(docElement, PARTICIPLETAG));
        answer.setRelations(getFacts(docElement, RELATIONTAG));
        answer.setSubjects(getFacts(docElement, SUBJECTTAG));
        answer.setObjects(getFacts(docElement, OBJECTTAG));
        answer.setNames(getFacts(docElement, NAMETAG));
        answer.setPrivileges(getFacts(docElement, PRIVILEGETAG));
        return answer;
    }

    public static List<Lead> getLeadList(String fileName){
        Lead answer = getAnswer(fileName);

        List<Lead> leads = new ArrayList<>();
        int leadCount = getLeadCount(answer);
        for (int i = 0; i < leadCount; i++) {
            Lead lead = new Lead();
            lead.setAdjectives(getFacts(answer.getAdjectives(), i));
            lead.setNouns(getFacts(answer.getNouns(), i));
            lead.setVerbs(getFacts(answer.getVerbs(), i));
            lead.setOthers(getFacts(answer.getOthers(), i));
            lead.setParts(getFacts(answer.getParts(), i));
            lead.setParticiples(getFacts(answer.getParticiples(), i));
            lead.setRelations(getFacts(answer.getRelations(), i));
            lead.setSubjects(getFacts(answer.getSubjects(), i));
            lead.setObjects(getFacts(answer.getObjects(), i));
            lead.setNames(getFacts(answer.getNames(), i));
            lead.setPrivileges(getFacts(answer.getPrivileges(), i));
            leads.add(lead);
        }

        return leads;
    }

    private static Document parseXmlFile(String filename) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document dom = null;
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(filename);
        } catch(ParserConfigurationException | SAXException | IOException pce) {
            pce.printStackTrace();
        }
        return dom;
    }

    private static ArrayList<Fact> getFacts(Element documentElement, String factTag) {
        NodeList nodes = documentElement.getElementsByTagName(factTag);
        ArrayList<Fact> facts = new ArrayList<>();
        if (nodes != null && nodes.getLength() > 0) {
            for(int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element)nodes.item(i);
                Node valueNode = element.getFirstChild();
                int id = Integer.parseInt(element.getAttribute("FactID"));
                int leadId = Integer.parseInt(element.getAttribute("LeadID"));
                int position = Integer.parseInt(element.getAttribute("pos"));
                String value = ((DeferredElementImpl) valueNode).getAttribute("val");

                facts.add(new Fact(id, leadId, position, value));
            }
        }
        return facts;
    }

    private static int getLeadCount(Lead answer) {
        int leadCount = 0;
        leadCount = getLeadCount(answer.getAdjectives(), leadCount);
        leadCount = getLeadCount(answer.getNouns(), leadCount);
        leadCount = getLeadCount(answer.getVerbs(), leadCount);
        leadCount = getLeadCount(answer.getOthers(), leadCount);
        leadCount = getLeadCount(answer.getParts(), leadCount);
        leadCount = getLeadCount(answer.getParticiples(), leadCount);
        leadCount = getLeadCount(answer.getRelations(), leadCount);
        leadCount = getLeadCount(answer.getSubjects(), leadCount);
        leadCount = getLeadCount(answer.getObjects(), leadCount);
        leadCount = getLeadCount(answer.getNames(), leadCount);
        leadCount = getLeadCount(answer.getPrivileges(), leadCount);

        return leadCount;
    }

    private static int getLeadCount(ArrayList<Fact> facts, int startCount) {
        int leadCount = startCount;
        for (Fact fact : facts) {
            int leadId = fact.getLeadId();
            if (leadId > leadCount - 1) leadCount = leadId + 1;
        }
        return leadCount;
    }

    private static ArrayList<Fact> getFacts(ArrayList<Fact> facts, int leadId) {
        ArrayList<Fact> filteredFacts = new ArrayList<>();
        for (Fact fact : facts) {
            if (fact.getLeadId() == leadId) filteredFacts.add(fact);
        }
        return filteredFacts;
    }
}
