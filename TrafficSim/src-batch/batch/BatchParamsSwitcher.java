package batch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BatchParamsSwitcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Evolutionary game theroy settings
		String popu_a = args[0];
		String popu_b = args[1];
		String density = args[2];
		String fitnessTime = args[3];
		String penalty = args[4];
		String max_ticks_without_moving = args[5];
		String batchDir = args[6];
		

		changeBatchParams(popu_a, popu_b, density, fitnessTime, penalty, max_ticks_without_moving, batchDir);
	}

	/**
	 * 
	 * @param totalComments
	 * @param numberAgents
	 * @param normViolationRate
	 * @param stopTick
	 */
	private static void changeBatchParams(String popu_a, String popu_b, String density, String fitnessTime, 
			String penalty, String max_ticks_without_moving, String batchDir){
		
		try {
			File file = new File(batchDir);
			if (!file.exists()) {
				file.createNewFile();
			}

			// Create instance of DocumentBuilderFactory
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			// Get the DocumentBuilder
			DocumentBuilder docBuilder = factory.newDocumentBuilder();

			// Using existing XML Document
			Document doc = docBuilder.parse(file);

			NodeList nList = doc.getElementsByTagName("parameter");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					
					/* Simulator Settings */
					
					if (eElement.getAttribute("name").equals("popu_a")) {
						eElement.setAttribute("value", "" + popu_a);
					}
					if (eElement.getAttribute("name").equals("popu_b")) {
						eElement.setAttribute("value", "" + popu_b);
					}
					if (eElement.getAttribute("name").equals("SimCarsDensity")) {
						eElement.setAttribute("value", "" + density);
					}
					if (eElement.getAttribute("name").equals("fitnessTime")) {
						eElement.setAttribute("value", "" + fitnessTime);
					}
					if (eElement.getAttribute("name").equals("penalty")) {
						eElement.setAttribute("value", "" + penalty);
					}
					if (eElement.getAttribute("name").equals("max_ticks_without_moving")) {
						eElement.setAttribute("value", "" + max_ticks_without_moving);
					}
				}

			}
			// set up a transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();

			// create string from xml tree
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(doc);
			trans.transform(source, result);
			String xmlString = sw.toString();

			OutputStream f0;

			byte buf[] = xmlString.getBytes();
			f0 = new FileOutputStream(batchDir);
			for (int i = 0; i < buf.length; i++) {
				f0.write(buf[i]);
			}
			f0.close();
			buf = null;
			System.out.println("BATCH PARAMETERS COPIED...");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

	}

}
