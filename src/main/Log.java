package main;
/**
 * Log class represents a log entry for a particular drawer.
 * Contains the log text and the timestamp.
 * Each Drawer object contains a Log object of its own.
 * 
 * @author KaranjitGahunia
 *
 */
public class Log {
	private String logEntry;
	private String logTimestamp;
	private int drawerId;
	
	/**
	 * Default Constructor.
	 * Initializes logEntry and logTimestamp to empty Strings.
	 * Initializes drawerId to parameter value.
	 * 
	 * @param drawerId - int
	 */
	public Log(int drawerId) {
		this.logEntry = "";
		this.logTimestamp = "";
		this.drawerId = drawerId;
	}

	/**
	 * Getter for logEntry
	 * 
	 * @return logEntry - String
	 */
	public String getLogEntry() {
		return logEntry;
	}

	/**
	 * Setter for logEntry
	 * 
	 * @param logEntry - String
	 */
	public void setLogEntry(String logEntry) {
		this.logEntry = logEntry;
	}

	/**
	 * Getter for logTimestamp
	 * 
	 * @return logTimestamp - String
	 */
	public String getLogTimestamp() {
		return logTimestamp;
	}

	/**
	 * Setter for logTimestamp
	 * 
	 * @param logTimestamp - String
	 */
	public void setLogTimestamp(String logTimestamp) {
		this.logTimestamp = logTimestamp;
	}
	
	/**
	 * Getter for drawerId
	 * 
	 * @return drawerId - int
	 */
	public int getDrawerId() {
		return drawerId;
	}

	/**
	 * Returns the URL for the log page the data was retrieved from.
	 * 
	 * @return URL - String
	 */
	public String getLogURL() {
		return "http://kiosk.aut.ac.nz/LockerBox/Drawers/Logs/" + drawerId;
	}

	/**
	 * String representation of this object.
	 * Returns a formatted string that contains the logEntry and logTimestamp 
	 * 
	 * @return String representation of this object
	 */
	@Override
	public String toString() {
		return logEntry + " || " + logTimestamp;
	}
	
}
