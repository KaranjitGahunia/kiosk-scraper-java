import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Contains functionality to scrape drawer data off webpages.
 * Retrieves the relevant data and stores it in Drawer objects.
 * In KioskThread class, the run method contains a Webscraper object to retrieve appropriate data. 
 * 
 * @author KaranjitGahunia
 *
 */
public class Webscraper {
	private Drawer[] drawers;
	
	/**
	 * Default constructor for Webscraper class.
	 * Initializes the drawers array.
	 * 
	 * @param drawers - Array for Drawer objects.
	 */
	public Webscraper(Drawer[] drawers) {
		this.drawers = drawers;
	}
	
	/**
	 * Invokes methods to update drawer data by scraping webpages.
	 * If log scraping is disabled, don't retrieve the log data.
	 * 
	 * @param logsEnabled - Boolean that is only true if log data should be scraped.
	 */
	public void scrapeData(boolean logsEnabled) {
		for (Drawer d : drawers) {
			getDrawerData(d);
			if (logsEnabled) {
				getLogData(d.getDrawerLog());
			}
		}
	}
	
	/**
	 * Retrieves a particular drawer's information from the edit page.
	 * Uses JSoup library to open appropriate drawer webpage as Document object.
	 * Invokes methods to retrieve information and stores in existing Drawer object.
	 * 
	 * @param drawer - Drawer object to store information in
	 */
	private void getDrawerData(Drawer drawer) {
		try {
			String url = "http://kiosk.aut.ac.nz/LockerBox/Drawers/Edit/" + drawer.getDrawerId();
			Connection conn = Jsoup.connect(url);
			Document doc = conn.get();
			
			drawer.setTakenOut(getCheckboxTagByID("IsTakenOut", doc));
			drawer.setOpen(getCheckboxTagByID("IsOpen", doc));
			drawer.setInError(getCheckboxTagByID("IsInError", doc));
			drawer.setRackId(getSelectTagByID("RackId", doc));
			drawer.setBarcode(getInputTagByID("BarcodeInDrawer", doc));
			drawer.setRfid(getInputTagByID("RFIDTagOnItem", doc));
			drawer.setLastStudent(getInputTagByID("StudentIDInLastWithdrawal", doc));
			drawer.setNote(getInputTagByID("Note", doc));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieves a particular drawer's log information from the logs page.
	 * Uses JSoup library to open appropriate logs webpage as Document object.
	 * Logs page loads all log entries. We only require the latest entry and timestamp.
	 * 
	 * @param log - Log object to store information in.
	 */
	private void getLogData(Log log) {
		try {
			String url = "http://kiosk.aut.ac.nz/LockerBox/Drawers/Logs/" + log.getDrawerId();
			Connection conn = Jsoup.connect(url);
			Document doc = conn.get();
			Elements e = doc.getElementsByTag("td");
			if (!e.isEmpty()) {
				log.setLogEntry(e.get(0).html());
				log.setLogTimestamp(e.get(1).html());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Function finds checkbox element in dom of particular ID and returns
	 * whether or not it is checked.
	 * 
	 * @param elementID - The ID value of the HTML tag to draw info from
	 * @param doc - The doc object that has loaded the HTML page
	 * @return isChecked - Returns string representing whether element is checked or not 
	 */
	private String getCheckboxTagByID(String elementID, Document doc) {
		Element checkboxElement = doc.getElementById(elementID);
		String isChecked = "";
		if (checkboxElement != null){
			// checked attribute only exists if the element is checked.
			// don't need to retrieve checked value. only check if it exists.
			isChecked = Boolean.toString(checkboxElement.hasAttr("checked"));
		}
		return isChecked;
	}
	
	/**
	 * Function finds input element in dom of particular ID and returns it's value.
	 * 
	 * @param elementID - The ID value of the HTML tag to draw info from
	 * @param doc - The doc object that has loaded the HTML page
	 * @return inputValue - Returns string of element value
	 */
	private String getInputTagByID(String elementID, Document doc) {
		Element inputElement = doc.getElementById(elementID);
		String inputValue = "";
		if (inputElement != null) {
			inputValue = inputElement.val();
		}
		return inputValue;
	}
	
	/**
	 * Function finds select element in dom of particular ID and returns the
	 * selected option's value.
	 * Note that this method is only used for the ID "RackId".
	 * 
	 * @param elementID - The ID value of the HTML tag to draw info from
	 * @param doc = The doc object that has loaded the HTML page
	 * @return inputValue - Returns string of element value
	 */
	private String getSelectTagByID(String elementID, Document doc) {
		Element inputElement = doc.getElementById(elementID);
		String selectValue = "";
		if (inputElement != null) {
			Elements optionElements = inputElement.getElementsByTag("option");
			for (int i = 0; i < optionElements.size(); i++) {
				if (optionElements.get(i).hasAttr("selected")) {
					selectValue = optionElements.get(i).val();
					break;
				}
			}
		}
		return selectValue;
	}
}
