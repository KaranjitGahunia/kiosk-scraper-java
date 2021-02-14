package main;

import gui.InitialWindow;

/**
 * Main class for this application
 * Contains the main method.
 * @author KaranjitGahunia
 *
 */
public class Main {

	
	/**
	 * Main method for this application.
	 * Creates an InitialWindow object.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		 new InitialWindow();
	}
	
	/*
	 * INPROGRESS
	 * - 
	 * 
	 * TO-DO
	 * - Generate favicons
	 * - Progress bar on close
	 * - Output to log.txt at close
	 * - JTable to contain buttons
	 * - Add icons to pages and GUI
	 * - Use JSplitPane to show kiosk status and load times in GUI 
	 * - Export to runnable
	 */
}

//public class Main {
//	
//	public static void main(String[] args) {
////		Kiosk north = new Kiosk(Location.NORTH);
//		Kiosk south = new Kiosk(Location.SOUTH);
////		Kiosk city = new Kiosk(Location.CITY);
//
//		Webscraper ws = null;
//		ws = new Webscraper(south.getKioskDrawers());
//		ws.scrapeData(true);
////		ws = new Webscraper(city.getKioskDrawers());
////		ws.scrapeData(true);
////		ws = new Webscraper(north.getKioskDrawers());
////		ws.scrapeData(true);
//		
//		JSONParser jparse = new JSONParser();
//		jparse.createJSON(south);
////		jparse.createJSON(city);
////		jparse.createJSON(north);
//		
//		System.out.println("Finished");
//	}
//}
