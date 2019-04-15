/**
 * Class used to contain scraped Drawer data.
 * 
 * @author KaranjitGahunia
 *
 */
public class Drawer {
	private int drawerNumber;
	private int drawerId;
	private String isTakenOut;
	private String isOpen;
	private String isInError;
	private String rackId;
	private String barcode;
	private String rfid;
	private String lastStudent;
	private String note;
	private Log drawerLog;

	/**
	 * Default Constructor
	 * Initializes drawerNumber, drawerId, and drawerLog values based on parameters.
	 * 
	 * @param drawerID - unique drawerID for drawer used to access individual pages.
	 * @param drawerNumber - unique drawer number representing order in respect to its kiosk.
	 */
	public Drawer(int drawerId, int drawerNumber) {
		this.drawerNumber = drawerNumber;
		this.drawerId = drawerId;
		this.drawerLog = new Log(drawerId);
	}
	
	/**
	 * Getter for drawerNumber variable.
	 * 
	 * @return drawerNumber - int
	 */
	public int getDrawerNumber() {
		return drawerNumber;
	}
	
	/**
	 * Getter for drawerId variable.
	 * 
	 * @return drawerId - int
	 */
	public int getDrawerId() {
		return drawerId;
	}

	/**
	 * Getter for isTakenOut
	 * 
	 * @return isTakenOut - String
	 */
	public String isTakenOut() {
		return isTakenOut;
	}

	/**
	 * Setter for isTakenOut variable.
	 * 
	 * @param isTakenOut - String
	 */
	public void setTakenOut(String isTakenOut) {
		this.isTakenOut = isTakenOut;
	}

	/**
	 * Getter for isOpen variable
	 * 
	 * @return isOpen - String
	 */
	public String isOpen() {
		return isOpen;
	}

	/**
	 * Setter for isOpen variable.
	 * 
	 * @param isOpen - String
	 */
	public void setOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	/**
	 * Getter for isInError variable
	 * 
	 * @return isInError - String
	 */
	public String isInError() {
		return isInError;
	}

	/**
	 * Setter for inError variable.
	 * 
	 * @param inError - String
	 */
	public void setInError(String isInError) {
		this.isInError = isInError;
	}

	/**
	 * Getter for rackId variable
	 * 
	 * @return rackId - String
	 */
	public String getRackId() {
		return rackId;
	}
	
	/**
	 * Modified setter for rackId
	 * Takes param string, parses to int, and uses modulo operation
	 * and condition to change value to 1-3.
	 * This is because scraped rackId value covers all kiosks so 
	 * values range from 1 to 8. Since we are only displaying data by kiosk,
	 * the value needs to be a maximum of 3. 
	 * 
	 * @param rackId - String form of rackId value
	 */
	public void setRackId(String rackId) {
		int parsedRackId = Integer.parseInt(rackId) % 3;
		if (parsedRackId == 0) {
			parsedRackId = 3;
		}
		this.rackId = Integer.toString(parsedRackId);
	}

	/**
	 * Getter for barcode variable.
	 * 
	 * @return barcode - String
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * Setter for barcode variable.
	 * 
	 * @param barcode - String
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	/**
	 * Getter for rfid variable.
	 * 
	 * @return rfid - String
	 */
	public String getRfid() {
		return rfid;
	}

	/**
	 * Setter for rfid variable.
	 * 
	 * @param rfid - String
	 */
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	/**
	 * Getter for lastStudent variable.
	 * 
	 * @return lastStudent - String
	 */
	public String getLastStudent() {
		return lastStudent;
	}

	/**
	 * Setter for lastStudent variable.
	 * 
	 * @param lastStudent - String
	 */
	public void setLastStudent(String lastStudent) {
		this.lastStudent = lastStudent;
	}

	/**
	 * Getter for note variable.
	 * 
	 * @return note - String
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Setter for note variable.
	 * 
	 * @param note - String
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * Getter for drawerLog variable.
	 * 
	 * @return drawerLog - Log object
	 */
	public Log getDrawerLog() {
		return drawerLog;
	}
	
	/**
	 * Setter for drawerLog variable.
	 * 
	 * @param drawerLog - Log object
	 */
	public void setDrawerLog(Log drawerLog) {
		this.drawerLog = drawerLog;
	}
	
	/**
	 * Returns string representation of the Drawer object.
	 * Compiles formatted text of each var in object and returns as String.
	 * Used by Kiosk class toString (for debugging).
	 * 
	 * @return output - String containing object representation.
	 */
	@Override
	public String toString() {
		String output = "DrawerID: " + drawerId + "\n";
		output += "Taken Out? " + isTakenOut + "\n";
		output += "Open? " + isOpen + "\n";
		output += "In Error? " + isInError + "\n";
		output += "Rack: " + rackId + "\n";
		output += "Barcode: " + barcode + "\n";
		output += "RFID: " + rfid + "\n";
		output += "Last Student: " + lastStudent + "\n";
		output += "Note: " + note + "\n";
		output += drawerLog.toString() + "\n\n";
		return output;
	}
}
