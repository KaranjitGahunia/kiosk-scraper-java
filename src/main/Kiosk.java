package main;

import java.time.LocalTime;

/**
 * Class used to contain data for a particular kiosk.
 * 
 * @author KaranjitGahunia
 *
 */
public class Kiosk {
	private Location kioskLocation;
	private Drawer[] kioskDrawers;
	private LocalTime timeDataRetrieved;
	
	/**
	 * Default constructor.
	 * Requires parameter kioskLocation. Used to retrieve drawerIDs
	 * Initializes kioskDrawers array with appropriate Drawer objects for this Kiosk.
	 * 
	 * @param kioskLocation - Location (North/South/City).
	 */
	public Kiosk(Location kioskLocation) {
		this.kioskLocation = kioskLocation;
		this.kioskDrawers = new Drawer[kioskLocation.getDrawerNumbers().length];
		for (int i = 0; i < kioskDrawers.length; i++) {
			kioskDrawers[i] = new Drawer(kioskLocation.getDrawerNumbers()[i], (i + 1));
		}
	}
	
	/**
	 * Getter for kioskLocation variable
	 * 
	 * @return kioskLocation - Location object
	 */
	public Location getKioskLocation() {
		return kioskLocation;
	}
	
	/**
	 * Getter for kioskDrawers variable
	 * 
	 * @return kioskDrawers - Array of Drawer objects
	 */
	public Drawer[] getKioskDrawers() {
		return kioskDrawers;
	}

	/**
	 * Setter for kioskDrawers variable
	 * 
	 * @param kioskDrawers - Array of Drawer objects
	 */
	public void setKioskDrawers(Drawer[] kioskDrawers) {
		this.kioskDrawers = kioskDrawers;
	}
	
	/**
	 * Getter for timeDataRetrieved variable.
	 * 
	 * @return timeDataRetrieved - LocalTime object
	 */
	public LocalTime getTimeDataRetrieved() {
		return timeDataRetrieved;
	}
	
	/**
	 * Setter for timeDataRetrieved variable.
	 * 
	 * @param timeDataRetrieved - LocalTime object
	 */
	public void setTimeDataRetrieved(LocalTime timeDataRetrieved) {
		this.timeDataRetrieved = timeDataRetrieved;
	}

	/**
	 * Returns string representation of Kiosk object.
	 * Iterates through the Kiosk drawers, invoking each drawers toString method and concatenating the results together.
	 * 
	 * @return output - String containing Kiosk object string representation 
	 */
	@Override
	public String toString(){
		String output = "";
		for (int i = 0; i < kioskDrawers.length; i++) {
			output += (kioskDrawers[i].toString());
		}
		return output;
	}
}
