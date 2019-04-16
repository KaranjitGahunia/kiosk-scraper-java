package gui;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JToggleButton;

import main.KioskThread;

/**
 * ItemListener implementation used to monitor when logs button is interacted with in GuiWindow class.
 * On action, it enables/disables the log scraping functionality of the program.
 * Each thread / kiosk location has a logs button.
 * 
 * @author KaranjitGahunia
 *
 */
public class LogsToggleListener implements ItemListener{
	
	private JToggleButton logsButton;
	private KioskThread thread;
	
	/**
	 * Default Constructor.
	 * Initializes the logsButton and thread variables.
	 * 
	 * @param logsButton - JToggleButton object that controls log scraping functionality.
	 * @param thread - KioskThread object for the particular kiosk at hand.
	 */
	public LogsToggleListener(JToggleButton logsButton, KioskThread thread) {
		this.logsButton = logsButton;
		this.thread = thread;
	}

	@Override
	public void itemStateChanged(ItemEvent eve) {
		if (logsButton.isSelected()) {
			// Following code enables log scraping functionality for this kiosk.
			// Changes button text to disable logs.
			logsButton.setText("Disable logs");
			// Enables log scraping within KioskThread object.
			thread.setLogScraping(true);
			// Output to JTextArea via sysout.
			System.out.println("> " + thread.getAKiosk().getKioskLocation().name()+ " log scraping enabled");
		} else {
			// Following code disables log scraping functionality for this kiosk.
			// Changes button text to enable logs.
			logsButton.setText("Enable logs");
			// Disables log scraping within KioskThrad object.
			thread.setLogScraping(false);
			// Output to JTextArea via sysout.
			System.out.println("> " + thread.getAKiosk().getKioskLocation().name()+ " log scraping disabled");
		}
	}
	
}
