import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JToggleButton;

/**
 * ItemListener implementation used to monitor when thread button is interacted with in GuiWindow class.
 * On action, it starts/pauses the thread for a particular kiosk.
 * Each thread / kiosk location has a thread button.
 * 
 * @author KaranjitGahunia
 *
 */
public class ThreadToggleListener implements ItemListener{

	private JToggleButton threadButton;
	private JToggleButton logsButton;
	private KioskThread thread;
	
	/**
	 * Default Constructor.
	 * Initializes the threadButton, logsButton, and thread variables.
	 * 
	 * @param threadButton - JToggleButton object that controls thread run status. 
	 * @param logsButton - JToggleButton object that controls log scraping functionality.
	 * @param thread - KioskThread object for the particular kiosk at hand.
	 */
	public ThreadToggleListener(JToggleButton threadButton, JToggleButton logsButton, KioskThread thread) {
		this.threadButton = threadButton;
		this.logsButton = logsButton;
		this.thread = thread;
	}
	
	@Override
	public void itemStateChanged(ItemEvent eve) {
		if (threadButton.isSelected()) {
			// Following code starts the thread.
			// Set pause in thread to false. This starts thread operations.
			thread.setPause(false);
			// Set button text to stop.
			threadButton.setText("Stop");
			// Enable logsButton button so that it can be interacted with.
			logsButton.setEnabled(true);
			// Output message to JTextArea via sysout
			System.out.println("> " + thread.getAKiosk().getKioskLocation().name()+ " thread enabled.");
		} else {
			// Set pause in thread to true. This stops thread operations.
			thread.setPause(true);
			// Set button text to start.
			threadButton.setText("Start");
			// Disable logsButton button so that it can not be interacted with.
			logsButton.setEnabled(false);
			// De-select logsButton so that when it is re-enabled, the default setting will be to not scrape logs.
			logsButton.setSelected(false);
			// Change text for logs button accordingly.
			logsButton.setText("Enable Logs");
			// Output message to JTextArea via sysout
			System.out.println("> " + thread.getAKiosk().getKioskLocation().name()+ " thread paused. Waiting for last execution to finish.");
		}
	}

}
