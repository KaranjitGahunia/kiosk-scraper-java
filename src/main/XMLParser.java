package main;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @deprecated
 * @author em12259
 *
 */
public class XMLParser {
	public static String directory;

	public void createXML(Kiosk kiosk) {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			Element trueRoot = document.createElement("root");
			document.appendChild(trueRoot);
			
			trueRoot.appendChild(addKioskToXML(document, kiosk));

			//transform the DOM Object to an XML File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			DOMSource domSource = new DOMSource(document);
			
//			File xmlOutput = new File(System.getProperty("user.dir") + "/src/web/" + kiosk.getKioskLocation().toString() + ".xml");
			File xmlOutput = new File(directory + "xml\\" + kiosk.getKioskLocation().toString() + ".xml");
			StreamResult streamResult = new StreamResult(xmlOutput);
			
			System.out.println("Output file to " + xmlOutput.getAbsolutePath());
			
			transformer.transform(domSource, streamResult);
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	private Element addKioskToXML(Document document, Kiosk kiosk) {
		Element root = document.createElement("kiosk");
		root.setAttribute("location", kiosk.getKioskLocation().toString());

		Drawer[] drawers = kiosk.getKioskDrawers();

		for (Drawer d : drawers) {
			Element e = addDrawerToRoot(document, d);
			root.appendChild(e);
		}
		
		return root;
	}

	private Element addDrawerToRoot(Document document, Drawer drawer) {
		Element drawerElement = document.createElement("drawer");
		
		Element drawerId = document.createElement("drawerId");
		drawerId.appendChild(document.createTextNode((Integer.toString(drawer.getDrawerId()))));
		drawerElement.appendChild(drawerId);

		Element drawerNumberElement = document.createElement("drawerNumber");
		drawerNumberElement.appendChild(document.createTextNode((Integer.toString(drawer.getDrawerNumber()))));
		drawerElement.appendChild(drawerNumberElement);

		/*
		 * Testing for full XML
		 */

		Element isTaken = document.createElement("isTaken");
		isTaken.appendChild(document.createTextNode(drawer.isTakenOut()));
		drawerElement.appendChild(isTaken);
		
		Element isOpen = document.createElement("isOpen");
		isOpen.appendChild(document.createTextNode(drawer.isOpen()));
		drawerElement.appendChild(isOpen);
		
		Element isInError = document.createElement("isInError");
		isInError.appendChild(document.createTextNode(drawer.isInError()));
		drawerElement.appendChild(isInError);
				
		/*
		 * End of testing
		 */
		
		Element rackId = document.createElement("rackId");
		rackId.appendChild(document.createTextNode(drawer.getRackId()));
		drawerElement.appendChild(rackId);

		Element barcode = document.createElement("barcode");
		barcode.appendChild(document.createTextNode(drawer.getBarcode()));
		drawerElement.appendChild(barcode);

		Element rfid = document.createElement("rfid");
		rfid.appendChild(document.createTextNode(drawer.getRfid()));
		drawerElement.appendChild(rfid);
		
		/*
		 * Testing for full XML
		 */
		
		Element lastStudent = document.createElement("lastStudent");
		lastStudent.appendChild(document.createTextNode(drawer.getLastStudent()));
		drawerElement.appendChild(lastStudent);
		
		Element note = document.createElement("note");
		note.appendChild(document.createTextNode(drawer.getNote()));
		drawerElement.appendChild(note);
		
		Element logEntry = document.createElement("logEntry");
		logEntry.appendChild(document.createTextNode(drawer.getDrawerLog().getLogEntry()));
		drawerElement.appendChild(logEntry);
		
		Element logTimestamp = document.createElement("logTimestamp");
		logTimestamp.appendChild(document.createTextNode(drawer.getDrawerLog().getLogTimestamp()));
		drawerElement.appendChild(logTimestamp);
		
		/*
		 * End of testing
		 */
		
		return drawerElement;
	}
}