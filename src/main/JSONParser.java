package main;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

public class JSONParser {
	public static String directory;
	
	public void createJSON(Kiosk kiosk) {
		
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		Gson gson = builder.create();
		
		String path = directory + "json\\" + kiosk.getKioskLocation().toString() + ".json";
		try {
			FileWriter fw = new FileWriter(path);
			gson.toJson(kiosk.getKioskDrawers(), fw);
			fw.close();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}	
