package trafficSim.XMLParser;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import repast.simphony.engine.environment.RunEnvironment;

/**
 * Class to manipulate or create XML files.
 * 
 * @author Iosu Mendizabal
 *
 */
public class XMLParser {

	String xmlFileName;
	ArrayList<EvolutionaryAgent> agente;
	ArrayList<norm> NS;

	/**
	 * First constructor of the XMLParser
	 * 
	 * @param xmlFileName
	 * 			Name of the XML file to write.
	 * @param agente
	 * 			ArrayList of the agents to save in the XML.
	 */
	public XMLParser(String xmlFileName, ArrayList<EvolutionaryAgent> agente) {
		this.xmlFileName = xmlFileName;
		this.agente = agente;
		writeXmlFile(agente);
		this.NS = new ArrayList<norm>();
	}

	/**
	 * Second and empty constructor of the XMLParser.
	 */
	public XMLParser() {
	}

	/**
	 * Method to write an XML file with a given ArrayList.
	 * 
	 * @param agente
	 * 			ArrayList of agents that we want to save in the XML.
	 */
	public void writeXmlFile(ArrayList<EvolutionaryAgent> agente) {
	
	}

	/**
	 * Method to read an XML file an return an agent ArrayList.
	 * @return 
	 * 
	 * @return The ArrayList of agents read from the XML file.
	 */
	public ArrayList<EvolutionaryAgent> getPopulationFromXML() {
		ArrayList<EvolutionaryAgent> agenteDeXML = new ArrayList<EvolutionaryAgent>();
		
		try {
			File fXmlFile=null;
//		File fXmlFile = new File("Experiments/population.xml");
			if(!RunEnvironment.getInstance().isBatch()) {
				fXmlFile = new File("experiments/files/populations/Population.xml");
			}else{
				fXmlFile = new File("experiments/files/populations/Population.xml");
			}
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("agent");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				this.NS = new ArrayList<norm>();
				Node nNode = nList.item(temp);
				int quantity;
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					quantity = Integer.parseInt(eElement.getElementsByTagName("quantity").item(0).getTextContent());
					
  					NodeList nList2 = doc.getElementsByTagName("norm");
  					String name = "";
  					int id = 0;
  					String precleft = "", precfront = "", precright = "";
  					for (int temp2 = 0; temp2 < nList2.getLength(); temp2++) {
  						Node nNode2 = nList.item(temp);
  						if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
  							Element eElement2 = (Element) nNode;
  							name = eElement.getElementsByTagName("name").item(0).getTextContent();
  							if(!name.equals("no norm")){
    							id = Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent());
    							precleft = eElement.getElementsByTagName("left").item(0).getTextContent();
    							precfront = eElement.getElementsByTagName("front").item(0).getTextContent();
    							precright = eElement.getElementsByTagName("right").item(0).getTextContent();
  							}
  						}
  					}
  					String modality = eElement.getElementsByTagName("modality").item(0).getTextContent();	
  					String action = eElement.getElementsByTagName("action").item(0).getTextContent();
  					//agenteDeXML.add(moderate);
  					if(!name.equals("no norm")){
  						norm n = new norm(name, id, precleft, precfront, precright, modality, action);
    					NS.add(n);
  					}	
					
					EvolutionaryAgent ea = new EvolutionaryAgent(quantity, NS);
					agenteDeXML.add(ea);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return agenteDeXML;
	}

}
