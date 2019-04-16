package main;
/**
 * Class used to provide average time values.
 * Stores the sum value of all time values added together.
 * Stores the amount of samples (amount of time values added together).
 * Uses these values (sum/samples) to provide average.
 * Used by KioskThread class to calculate and print average load times.
 * 
 * @author KaranjitGahunia
 *
 */
public class LoadTime {
	private long sum;
	private int samples;
	
	/**
	 * Updates the LoadTime object with a new entry.
	 * Adds the newTime value to sum and increments samples.
	 * 
	 * @param newTime - long time value
	 */
	public void update(long newTime) {
		samples++;
		sum = sum + newTime;;
	}
	
	/**
	 * Private method used by the toString method.
	 * Calculates and returns the average load time.
	 * Divides the sum value by samples.
	 * If samples are 0, return 0.
	 * 
	 * @return long - sum divided by samples.
	 */
	private long average() {
		if (samples == 0) {
			return 0;
		}
		return sum / samples;
	}
	
	/**
	 * String representation of the LoadTime object.
	 * Returns a formatted string containing the average time in seconds format.
	 * 
	 * @return String - LoadTime object String representation.
	 */
	@Override
	public String toString() {
		return "Average: " + average() / 1000 + " seconds";
	}
}
