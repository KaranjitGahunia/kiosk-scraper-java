package main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Class containing operations to output log data in HTML form to files.
 * Each KioskThread object has a FilePrinter object that it uses to call these methods.
 * Class has static variable directory that stores the absolute file path in which 
 * the files to edit are located.
 * Since there are no variables within this class, it uses a default constructor.
 * @author KaranjitGahunia
 *
 */
public class FilePrinter {
	
	public static String directory;
	
	/**
	 * Only public method in FilePrinter class.
	 * Called to print all scraped log data to the HTML pages.
	 * 
	 * @param aKiosk - Kiosk object to be printed. Contains log data
	 * @param average - Average time taken to scrape data and update file. 
	 * @param logsEnabled - Boolean value displaying whether log scraping is enabled or not.
	 */
	public void printToFile(Kiosk aKiosk, String average, boolean logsEnabled) {
		// File path for this particular kiosk's HTML page.
		String url = directory + "\\" + aKiosk.getKioskLocation().toString() + ".html";
		// Open file located at that file path.
		File input = new File(url);
		try {
			// Create document object by parsing the file and retrieve the "dataContainer" table.
			Document doc = Jsoup.parse(input, "UTF-8");
			Element tableElement = doc.getElementById("dataContainer");
			
			// Empty the table
			// This table contains the previous data entries that need to be replaced.
			tableElement.empty();
			
			// Call private method to add table header elements to document
			addHeaderElements(tableElement, logsEnabled);
			
			Drawer[] kioskDrawers = aKiosk.getKioskDrawers();
			for (int i = 0; i < kioskDrawers.length; i++) {
				addDrawerElements(tableElement, kioskDrawers[i], kioskDrawers[i].getDrawerLog());
			}
		
			addTimestamps(aKiosk, average, doc);
			
			FileWriter fw = new FileWriter(url);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(doc.outerHtml());
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Private method called by printToFile method.
	 * Adds the basic headings <th> for each column of the table.
	 * If log scraping is disabled, it is mentioned in the header for the log fields.
	 * 
	 * @param tableElement - the HTML table element to add the header entries to.
	 * @param logsEnabled - boolean variable. True is logs enabled.
	 */
	private void addHeaderElements(Element tableElement, boolean logsEnabled) {
		Element headerRow = new Element("tr");
		headerRow.appendTo(tableElement);
		Element drawerNumber = new Element("th").appendTo(headerRow);
		drawerNumber.append("Drawer Number");
		Element checkedOut= new Element("th").appendTo(headerRow);
		checkedOut.append("Checked Out");
		Element open = new Element("th").appendTo(headerRow);
		open.append("Open");
		Element inError = new Element("th").appendTo(headerRow);
		inError.append("In Error");
		Element rack = new Element("th").appendTo(headerRow);
		rack.append("Rack");
		Element barcode = new Element("th").appendTo(headerRow);
		barcode.append("Barcode");
		Element rfid = new Element("th").appendTo(headerRow);
		rfid.append("RFID");
		Element lastStudent = new Element("th").appendTo(headerRow);
		lastStudent.append("Last Student");
		Element note = new Element("th").appendTo(headerRow);
		note.append("Note");
		Element logEntry = new Element("th").appendTo(headerRow);
		Element logTimestamp = new Element("th").appendTo(headerRow);
		if (logsEnabled) {
			logEntry.append("Log Entry");
			logTimestamp.append("Log Timestamp");
		} else {
			logEntry.append("Log Entry (Updates Disabled)");			
			logTimestamp.append("Log Timestamp (Updates Disabled)");
		}

	}
	
	/**
	 * Private method called by printToFile method for each drawer in kiosk.
	 * Adds a row <tr> of drawer data <td> elements to the tableElement parameter.
	 * 
	 * @param tableElement - the HTML table element to add the header entries to.
	 * @param drawer - drawer object containing all scraped data for that drawer.
	 * @param log - log object for that particular drawer containing the last entry and time-stamp.
	 */
	private void addDrawerElements(Element tableElement, Drawer drawer, Log log) {
		Element headerRow = new Element("tr");
		headerRow.appendTo(tableElement);
		Element drawerNumber = new Element("td").appendTo(headerRow);
		drawerNumber.append("<a target=\"_blank\" href=\"http://kiosk.aut.ac.nz/LockerBox/Drawers/Edit/" + Integer.toString(drawer.getDrawerId()) + "\"> " + Integer.toString(drawer.getDrawerNumber()));
		Element checkedOut= new Element("td").appendTo(headerRow);
		checkedOut.append(drawer.isTakenOut());
		Element open = new Element("td").appendTo(headerRow);
		open.append(drawer.isOpen());
		Element inError = new Element("td").appendTo(headerRow);
		inError.append(drawer.isInError());
		Element rack = new Element("td").appendTo(headerRow);
		rack.append(drawer.getRackId());
		Element barcode = new Element("td").appendTo(headerRow);
		barcode.append(drawer.getBarcode());
		Element rfid = new Element("td").appendTo(headerRow);
		rfid.append(drawer.getRfid());
		Element lastStudent = new Element("td").appendTo(headerRow);
		lastStudent.append(drawer.getLastStudent());
		Element note = new Element("td").appendTo(headerRow);
		note.append(drawer.getNote());
		Element logEntry = new Element("td").appendTo(headerRow);
		logEntry.append("<a target=\"_blank\" href=\"" + log.getLogURL() + "\">" + log.getLogEntry());
		Element logTimestamp = new Element("td").appendTo(headerRow);
		logTimestamp.append(log.getLogTimestamp());
	}
	
	/**
	 * Private method called by printToFile method.
	 * Adds information about when the page was last updated and the average update time.
	 * The information is formatted and added to the "footertimestamp" <div> element of the page.
	 * 
	 * @param aKiosk - Kiosk object containing the data for all drawers of a particular kiosk.
	 * @param average - String representation of the average time taken to update the page.
	 * @param doc - Document object of the file being edited.
	 */
	private void addTimestamps(Kiosk aKiosk, String average, Document doc) {
		Element timestampDiv = doc.getElementById("footertimestamp");
		timestampDiv.empty();
		String kioskStamp = formatTimestamp(aKiosk.getTimeDataRetrieved());		
		timestampDiv.append("Data updated: " + kioskStamp +  " | " + average);
	}
	
	/**
	 * Private method called by addTimestamps method.
	 * Takes parameter LocalTime object, parses to String, and removes the milliseconds from it.
	 * 
	 * @param timestamp LocalTime object to be altered.
	 * @return Formatted String representation of the time-stamp.
	 */
	private String formatTimestamp(LocalTime timestamp) {
		return timestamp.toString().split("\\.")[0];
	}
}
