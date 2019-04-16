package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;

import main.KioskThread;

/**
 * ActionListener implementation used to monitor when exit button is interacted with in GuiWindow class.
 * On action, it stops all operations and safely closes threads.
 * 
 * @author KaranjitGahunia
 *
 */
public class ExitButtonListener implements ActionListener {
	private JComponent[] buttons;
	private KioskThread north;
	private KioskThread city;
	private KioskThread south;

	/**
	 * Constructor for ExitButtonListener objects.
	 * Takes four parameters and stores them within this new object.
	 * north, city, and south are all KioskThread objects while buttons is an array of JComponent objects.
	 * buttons stores all the JButtons and JToggleButtons in GuiWindow class. This is used to disable them on action.
	 * 
	 * @param buttons - Array of JComponents. Should store JToggleButtons or JButtons
	 * @param north - KioskThread object for north
	 * @param city - KioskThread object for city
	 * @param south - KioskThread object for south
	 */
	public ExitButtonListener(JComponent[] buttons, KioskThread north, KioskThread city, KioskThread south) {
		this.north = north;
		this.city = city;
		this.south = south;
		this.buttons = buttons;
	}
	
	@Override
	public void actionPerformed(ActionEvent eve) {
		// Create new runnable thread to conduct closing operations concurrently.
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				// Disable each button in buttons array
				for (JComponent button : buttons) {
					button.setEnabled(false);
				}	
				try {
					// Call terminate thread method for each kiosk to escape run loop.
					System.out.println("> Stopping all threads. Please wait");
					north.terminateThread();
					south.terminateThread();
					city.terminateThread();
					
					// While not terminated, loop and sleep. Else print message
					while(north.isThreadAlive()) {
						Thread.sleep(500);
					}
					System.out.println("> NORTH thread terminated");
					
					// While not terminated, loop and sleep. Else print message
					while(south.isThreadAlive()) {
						Thread.sleep(500);
					}
					System.out.println("> SOUTH thread terminated");
					
					// While not terminated, loop and sleep. Else print message
					while(city.isThreadAlive()) {
						Thread.sleep(500);
					}
					System.out.println("> CITY thread terminated");
					
					// Pause and print message
					Thread.sleep(500);
					System.out.println("> Safely closing program");
					
					// Terminate program
					Thread.sleep(2000);
					System.exit(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
		});
		t.start();
	}

}
