/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simplesearcher;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author nogaevpn
 */
public class ConfigReader  {

    //public static final ConfigReader INSTANCE = new ConfigReader();
    private static ConfigReader instance;
    public List<Source> sources;
    private static final String filename = "configuration.xml";
    
    public static synchronized ConfigReader getInstance() throws ParserConfigurationException, SAXException, IOException {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    private ConfigReader() throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Load the input XML document, parse it and return an instance of the Document class.
        Document document = builder.parse(new File(filename));

        this.sources = new ArrayList<Source>();
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                // Get the value of all sub-elements.
                try {
                    String serverName = elem.getElementsByTagName("ServerName")
                            .item(0).getChildNodes().item(0).getNodeValue();

                    Integer portNumber = Integer.parseInt(elem.getElementsByTagName("PortNumber")
                            .item(0).getChildNodes().item(0).getNodeValue());

                    String databaseName = elem.getElementsByTagName("DatabaseName").item(0)
                            .getChildNodes().item(0).getNodeValue();

                    String user = elem.getElementsByTagName("User").item(0)
                                            .getChildNodes().item(0).getNodeValue();

                    String password = elem.getElementsByTagName("Password").item(0)
                                            .getChildNodes().item(0).getNodeValue();
                    
                    this.sources.add(new Source(serverName, portNumber, databaseName, user, password));
                } catch (Exception e) {
                    throw new ParserConfigurationException("Incorrect config file");
                }
            }
        }

    }

}
