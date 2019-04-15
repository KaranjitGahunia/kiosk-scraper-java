import java.time.LocalTime;

/**
 * Class used to contain the scraped log entries for a kiosk.
 * Note - Not used anymore. Logs moved to Drawer objects.
 * 
 * @deprecated Since adding Log object to Drawer and changing WebScraper
 * @author KaranjitGahunia
 *
 */
@Deprecated
public class KioskLogs {
	private Log[] logs;
	private LocalTime lastUpdated;
	private int[] drawerIds;
	
	/**
	 * Default constructor.
	 * Initializes the drawerIds variable based on parameter.
	 * Then creates an array of Log entries based on parameter array length.
	 * Creates a new Log object for every space in the log array.
	 * 
	 * @param drawerIds
	 */
	public KioskLogs(int[] drawerIds) {
		this.drawerIds = drawerIds;
		this.logs= new Log[drawerIds.length];
		for (int i = 0; i < logs.length; i++) {
			logs[i] = new Log(i);
		}
	}

	/**
	 * Getter for logs variable
	 * 
	 * @return logs - Array of Log objects
	 */
	public Log[] getLogs() {
		return logs;
	}
	
	/**
	 * Setter for logs variable
	 * 
	 * @param logs - Array of Log objects.
	 */
	public void setLogs(Log[] logs) {
		this.logs = logs;
	}
	
	/**
	 * Getter for lastUpdated variable.
	 * 
	 * @return lastUpdated - LocalTime
	 */
	public LocalTime getLastUpdated() {
		return lastUpdated;
	}
	
	/**
	 * Setter for lastUpdated variable.
	 * 
	 * @param lastUpdated - LocalTime
	 */
	public void setLastUpdated(LocalTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	/**
	 * Getter for drawerIds variable.
	 * 
	 * @return drawerIds - int array
	 */
	public int[] getDrawerIds() {
		return drawerIds;
	}
}
