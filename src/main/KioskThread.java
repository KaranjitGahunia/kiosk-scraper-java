package main;
import java.time.LocalTime;

/**
 * Class representing a thread that will scrape and print data for a particular kiosk.
 * Contains thread operations and controls
 * Used by class GuiWindow. Implements Runnable interface.
 * 
 * @author KaranjitGahunia
 *
 */
public class KioskThread implements Runnable {

	private Kiosk aKiosk;
	private Thread thread;
	private boolean pause;
	private boolean logScraping;
	private boolean terminate;
	
	/**
	 * Default constructor for KioskThread class.
	 * 
	 * @param aKiosk - Kiosk that this thread will be scraping and printing data for.
	 */
	public KioskThread(Kiosk aKiosk){
		this.aKiosk = aKiosk;
		// Set pause to true as initially the threads aren't running. Enabled through GUI.
		this.pause = true;
		// False for logScraping. Enabled through GUI.
		this.logScraping = false;
		// False for terminate. Enabled through GUI by clicking Exit.
		this.terminate = false;
	}
	
	/**
	 * Inherited method from Runnable interface.
	 * Creates a new Thread for this object and starts it.
	 */
	public void start() {
		this.thread = new Thread(this);
		thread.setName(aKiosk.getKioskLocation().name());
		thread.start();
	}
	
	/**
	 * Inherited method from Runnable interface.
	 * Method is called when thread is started by start method.
	 * Uses WebScraper object to retrieve kiosk data and FilePrinter object to print.
	 * Loops until boolean terminate isn't true.
	 * Times how long each loop takes and stores in LoadTime object.
	 */
	@Override
	public void run() {
		// Create local objects to use in this method.
		FilePrinter fp = new FilePrinter();
		LoadTime lt = new LoadTime();
		Webscraper scraper = new Webscraper(aKiosk.getKioskDrawers());
		// Loop thread operation until terminate isn't true.
		while (!terminate) {
			// If pause is true, sleep this thread for 3 seconds and go back to top of loop.
			if (pause) {
				try {
					Thread.sleep(1000);
					continue;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// Store system time at start of updating process.
			long startTime = System.currentTimeMillis();
			// Get kiosk data.
			scraper.scrapeData(logScraping);
			aKiosk.setTimeDataRetrieved(LocalTime.now());
			// Print data to file.
			fp.printToFile(aKiosk, lt.toString(), logScraping);
			// Update the LoadTime object with how long this update took.
			// Current system time - startTime var gives you how long it took in milliseconds.
			lt.update((System.currentTimeMillis() - startTime));
			// Print to sysout (possibly GUI JTextArea).
			System.out.println(aKiosk.getKioskLocation().name() + " updated in " + (System.currentTimeMillis() - startTime) + "ms");
		}
	}
	
	/**
	 * Getter for aKiosk variable
	 * 
	 * @return aKiosk - Kiosk object
	 */
	public Kiosk getAKiosk() {
		return this.aKiosk;
	}
	
	/**
	 * Setter for pause variable
	 * 
	 * @param pause - boolean
	 */
	public void setPause(boolean pause) {
		this.pause = pause;
		this.logScraping = false;
	}
	
	/**
	 * Setter for logScraping
	 * 
	 * @param logScraping - boolean
	 */
	public void setLogScraping(boolean logScraping) {
		this.logScraping = logScraping;
	}
	
	/**
	 * Sets terminate variable to true
	 * Called to kill thread safely.
	 */
	public void terminateThread() {
		this.terminate = true;
	}
	
	/**
	 * Checks to see if the thread is still alive.
	 * 
	 * @return boolean
	 */
	public boolean isThreadAlive() {
		return thread.isAlive();
	}
}
