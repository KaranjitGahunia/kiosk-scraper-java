/**
 * Enum used to associate Kiosk location and drawer numbers for that particular Kiosk.
 * Class Kiosk uses enum Location.
 * 
 * @author KaranjitGahunia
 *
 */
public enum Location {
	CITY (1,2,3,4,5,6,7,8,9,10,11,14,13,15,16,17,18,19,20,21,12,22,23,24,25,56,73,74,75,76),
	NORTH (57,58,59,60,62,63,64,65,66,67,61,68,69,70,71,72),
	SOUTH (26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55);
	
	/**
	 * Array that contains drawerIDs based on the location of Kiosk. 
	 */
	private final int[] drawerNumbers;
	
	/**
	 * Default constructor
	 * Initializes drawerNumbers based on what Location value is selected (City/North/South).
	 * 
	 * @param drawerNumbers - All arguments accompanied by selection of Location.
	 */
	Location (int... drawerNumbers){
		this.drawerNumbers = drawerNumbers;
	}
	
	/**
	 * Simple getter for drawerNumbers.
	 * 
	 * @return this.drawerNumbers
	 */
	public int[] getDrawerNumbers() {
		return this.drawerNumbers;
	}
	
	/**
	 * String representation of Location object.
	 * Returns location (city/north/south) in lower case.
	 * Used in FilePrinter class to form filepath URL.
	 * 
	 * @return String - Location object String representation.
	 */
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
