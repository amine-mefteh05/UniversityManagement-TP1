import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Main {

    private static final String XML_FILE = "/university.xml";
    private static final String XSD_FILE = "/university.xsd";

    public static void main(String[] args) {
        System.out.println("=== 1. Validation XML / XSD ===");
        if (!validerXml()) {
            System.out.println("Arret du programme : le XML est invalide.");
            return;
        }

        System.out.println();
        System.out.println("=== 2. Interrogation XPath ===");
        interrogerXPath();
    }

    // Etape 4 : validation du XML avec le schema XSD
    private static boolean validerXml() {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(Main.class.getResource(XSD_FILE));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(Main.class.getResourceAsStream(XML_FILE)));
            System.out.println("Le fichier university.xml est VALIDE.");
            return true;
        } catch (Exception e) {
            System.out.println("Le fichier university.xml est INVALIDE : " + e.getMessage());
            return false;
        }
    }

    // Etape 5 : requetes XPath
    private static void interrogerXPath() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            Document doc = dbf.newDocumentBuilder().parse(Main.class.getResourceAsStream(XML_FILE));

            XPath xpath = XPathFactory.newInstance().newXPath();

            afficher(xpath, doc,
                    "Noms de tous les etudiants :",
                    "/university/students/student/name");

            afficher(xpath, doc,
                    "Titres des cours :",
                    "/university/courses/course/title");

            afficher(xpath, doc,
                    "Etudiants du niveau LSI3 :",
                    "/university/students/student[level='LSI3']/name");

        } catch (Exception e) {
            System.out.println("Erreur XPath : " + e.getMessage());
        }
    }

    // Compile l'expression XPath, l'evalue et affiche chaque resultat
    private static void afficher(XPath xpath, Document doc, String titre, String expression)
            throws Exception {
        XPathExpression expr = xpath.compile(expression);
        NodeList resultats = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        System.out.println();
        System.out.println(titre + "  [" + expression + "]");
        for (int i = 0; i < resultats.getLength(); i++) {
            System.out.println(" - " + resultats.item(i).getTextContent());
        }
    }
}
